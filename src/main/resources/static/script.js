const API = "/api";

const areaSelect = document.getElementById("areaSelect");
const grupoSelect = document.getElementById("grupoSelect");
const grid = document.getElementById("speeches");
const toast = document.getElementById("toast");

// Form elements
const modeSwitch = document.getElementById("modeSwitch");
const switchLabel = document.getElementById("switchLabel");
const formTitle = document.getElementById("formTitle");
const btnPost = document.getElementById("btnPost");
const helper = document.getElementById("helper");

// Speech fields
const speechFields = document.getElementById("speechFields");
const titulo = document.getElementById("titulo");
const contenido = document.getElementById("contenido");

// Group fields
const groupFields = document.getElementById("groupFields");
const grupoNombre = document.getElementById("grupoNombre");
const grupoDescripcion = document.getElementById("grupoDescripcion");

// Estado del modo: false = speech, true = grupo
let modeIsGroup = false;

// Cargar áreas al iniciar
fetch(`${API}/areas`)
  .then(res => res.json())
  .then(areas => {
    areas.forEach(a => {
      const opt = document.createElement("option");
      opt.value = a.id;
      opt.textContent = a.nombre;
      areaSelect.appendChild(opt);
    });
  });

// Cambiar de área → cargar grupos
areaSelect.addEventListener("change", () => {
  grupoSelect.innerHTML = '<option value="">-- Selecciona un grupo --</option>';
  grupoSelect.disabled = true;
  grid.innerHTML = "";
  const areaId = areaSelect.value;
  if (!areaId) return;

  fetch(`${API}/grupos?areaId=${areaId}`)
    .then(res => res.json())
    .then(grupos => {
      grupos.forEach(g => {
        const opt = document.createElement("option");
        opt.value = g.id;
        opt.textContent = g.nombre;
        opt.dataset.nombre = g.nombre;
        grupoSelect.appendChild(opt);
      });
      grupoSelect.disabled = false;
      // Al cambiar de área, refrescar ayudas
      updateHelper();
    });
});

// Cambiar de grupo → listar speeches
grupoSelect.addEventListener("change", () => listarSpeeches());

// Switch de modo
modeSwitch.addEventListener("change", () => {
  modeIsGroup = modeSwitch.checked;
  if (modeIsGroup) {
    // Modo Grupo
    formTitle.textContent = "Postear un Grupo";
    switchLabel.textContent = "Grupo";
    btnPost.textContent = "Crear Grupo";
    speechFields.classList.add("hidden");
    groupFields.classList.remove("hidden");
  } else {
    // Modo Speech
    formTitle.textContent = "Postear un Speech";
    switchLabel.textContent = "Speech";
    btnPost.textContent = "Crear Speech";
    speechFields.classList.remove("hidden");
    groupFields.classList.add("hidden");
  }
  updateHelper();
});

// Botón postear (crear speech o grupo)
btnPost.addEventListener("click", () => {
  if (modeIsGroup) {
    // Crear grupo (requiere área seleccionada)
    const areaId = areaSelect.value;
    const nombre = grupoNombre.value.trim();
    const descripcion = grupoDescripcion.value.trim();

    if (!areaId) { alert("Selecciona un área para crear el grupo."); return; }
    if (!nombre) { alert("Ingresa el nombre del grupo."); return; }

    fetch(`${API}/grupos`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ areaId: Number(areaId), nombre, descripcion })
    })
      .then(res => {
        if (!res.ok) throw new Error("Error al crear el grupo");
        return res.json();
      })
      .then(g => {
        // limpiar y recargar lista de grupos
        grupoNombre.value = "";
        grupoDescripcion.value = "";
        return fetch(`${API}/grupos?areaId=${areaId}`)
          .then(r => r.json())
          .then(grupos => {
            grupoSelect.innerHTML = '<option value="">-- Selecciona un grupo --</option>';
            grupos.forEach(gg => {
              const opt = document.createElement("option");
              opt.value = gg.id;
              opt.textContent = gg.nombre;
              grupoSelect.appendChild(opt);
            });
            // seleccionar el nuevo grupo creado
            grupoSelect.value = g.id;
            listarSpeeches();
          });
      })
      .catch(() => alert("No se pudo crear el grupo."));
  } else {
    // Crear speech (requiere grupo seleccionado)
    const grupoId = grupoSelect.value;
    const t = titulo.value.trim();
    const c = contenido.value.trim();

    if (!grupoId) { alert("Selecciona un grupo para crear el speech."); return; }
    if (!t || !c) { alert("Completa título y contenido."); return; }

    fetch(`${API}/speeches?grupoId=${grupoId}`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ titulo: t, contenido: c }),
    })
      .then(res => {
        if (!res.ok) throw new Error("Error al crear el speech");
        return res.json();
      })
      .then(() => {
        titulo.value = ""; contenido.value = "";
        listarSpeeches();
      })
      .catch(() => alert("No se pudo crear el speech."));
  }
});

// Listar speeches del grupo (en tarjetas)
function listarSpeeches() {
  const grupoId = grupoSelect.value;
  if (!grupoId) { grid.innerHTML = ""; updateHelper(); return; }

  fetch(`${API}/speeches?grupoId=${grupoId}`)
    .then(res => res.json())
    .then(speeches => renderSpeeches(speeches));
}

function renderSpeeches(items) {
  grid.innerHTML = "";
  const grupoNombreText = grupoSelect.options[grupoSelect.selectedIndex]?.textContent || "Grupo";

  items.forEach(s => {
    const card = document.createElement("div");
    card.className = "speech-card";

    const head = document.createElement("div");
    head.className = "speech-head";

    const title = document.createElement("div");
    title.className = "speech-title";
    title.textContent = s.titulo;

    const actions = document.createElement("div");
    actions.className = "speech-actions";

    const copyBtn = document.createElement("button");
    copyBtn.className = "icon-btn";
    copyBtn.title = "Copiar contenido";
    copyBtn.innerHTML = `
      <svg class="icon" viewBox="0 0 24 24" aria-hidden="true">
        <path d="M16 1H4c-1.1 0-2 .9-2 2v12h2V3h12V1zm3 4H8c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h11c1.1 0 2-.9 2-2V7c0-1.1-.9-2-2-2zm0 16H8V7h11v14z"/>
      </svg>
    `;
    copyBtn.addEventListener("click", () => copyText(s.contenido));

    actions.appendChild(copyBtn);
    head.appendChild(title);
    head.appendChild(actions);

    const content = document.createElement("div");
    content.className = "speech-content";
    content.textContent = s.contenido;

    const footer = document.createElement("div");
    footer.className = "speech-footer";

    const badge = document.createElement("div");
    badge.className = "badge";
    badge.textContent = grupoNombreText;

    const del = document.createElement("button");
    del.className = "delete";
    del.textContent = "Eliminar";
    del.addEventListener("click", () => eliminarSpeech(s.id));

    footer.appendChild(badge);
    footer.appendChild(del);

    card.appendChild(head);
    card.appendChild(content);
    card.appendChild(footer);
    grid.appendChild(card);
  });

  updateHelper();
}

// Copiar al portapapeles
function copyText(text) {
  if (!navigator.clipboard) {
    const ta = document.createElement("textarea");
    ta.value = text;
    document.body.appendChild(ta);
    ta.select();
    try { document.execCommand("copy"); showToast("Copiado"); } finally { document.body.removeChild(ta); }
    return;
  }
  navigator.clipboard.writeText(text).then(() => showToast("Copiado"));
}

// Eliminar
function eliminarSpeech(id) {
  if (!confirm("¿Eliminar este speech?")) return;
  fetch(`${API}/speeches/${id}`, { method: "DELETE" })
    .then(() => listarSpeeches());
}

// Toast
let toastTimer;
function showToast(msg) {
  toast.textContent = msg;
  toast.classList.add("show");
  clearTimeout(toastTimer);
  toastTimer = setTimeout(() => toast.classList.remove("show"), 1400);
}

// Mensaje de ayuda según modo/selección
function updateHelper() {
  if (modeIsGroup) {
    helper.textContent = areaSelect.value
      ? "Creará un Grupo en el Área seleccionada."
      : "Selecciona un Área para poder crear un Grupo.";
  } else {
    helper.textContent = grupoSelect.value
      ? "Creará un Speech en el Grupo seleccionado."
      : "Selecciona un Grupo para poder crear un Speech.";
  }
}
