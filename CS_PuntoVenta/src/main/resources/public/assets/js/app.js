// ===========================================================
//   SESIONES Y AUTORIZACIÓN (USANDO ENDPOINTS DEL BACKEND)
// ===========================================================

// Llamado al backend para saber el estado de la sesión
async function obtenerSesion() {
    try {
        const res = await fetch("/api/session/info");
        return await res.json();
    } catch (e) {
        console.error("Error obteniendo sesión:", e);
        return { status: "no-session" };
    }
}

// Verifica acceso por tipo de usuario (Administrador / Comprador)
async function verificarAcceso(tipoNecesario) {
    const ses = await obtenerSesion();

    if (ses.status === "no-session") {
        alert("Debes iniciar sesión.");
        window.location.href = "login.html";
        return false;
    }

    if (tipoNecesario && ses.tipoUsuario !== tipoNecesario) {
        alert("No tienes permisos para acceder a esta página.");
        if (ses.tipoUsuario === "Administrador") {
            window.location.href = "inventario.html";
        } else {
            window.location.href = "home.html";
        }
        return false;
    }

    return true;
}

// Cerrar sesión
async function cerrarSesion() {
    try {
        await fetch("/api/logout", { method: "POST" });
    } catch (e) {
        console.error(e);
    }
    window.location.href = "login.html";
}


// ===========================================================
//   LOGIN
// ===========================================================
async function login() {
    const correo = document.getElementById("correo").value.trim();
    const pass = document.getElementById("password").value.trim();

    if (!correo || !pass) {
        alert("Llena correo y contraseña");
        return;
    }

    try {
        let res = await fetch("/api/login", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: `identificador=${encodeURIComponent(correo)}&password=${encodeURIComponent(pass)}`
        });

        let json = await res.json();

        if (json.status === "ok") {
            if (json.tipoUsuario === "Administrador") {
                window.location.href = "inventario.html";
            } else {
                window.location.href = "home.html";
            }
        } else {
            alert(json.msg || "Credenciales incorrectas");
        }

    } catch (e) {
        console.error(e);
        alert("Error en el servidor.");
    }
}


// ===========================================================
//   HOME: LISTA DE PRODUCTOS
// ===========================================================
async function cargarProductos() {
    const grid = document.getElementById("productsGrid");
    if (!grid) return;

    try {
        let res = await fetch("/api/productos");
        let productos = await res.json();

        grid.innerHTML = "";

        productos.forEach(p => {
            grid.innerHTML += `
                <article class="product-card" onclick="verProducto('${p.idProducto}')">
                    <img src="${p.imagen || 'https://via.placeholder.com/200x150'}" alt="${p.nombreProducto}">
                    <div class="product-name">${p.nombreProducto}</div>
                    <div class="product-price">$${p.precio} MXN</div>
                </article>
            `;
        });

    } catch (e) {
        console.error(e);
        alert("Error cargando productos");
    }
}

function verProducto(id) {
    window.location.href = `product.html?id=${encodeURIComponent(id)}`;
}

// ===========================================================
//   DETALLE DE PRODUCTO
// ===========================================================
async function cargarDetalleProducto() {
    const detail = document.getElementById("productDetail");
    if (!detail) return;

    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");
    if (!id) return;

    try {
        const res = await fetch(`/api/producto/${encodeURIComponent(id)}`);
        const p = await res.json();

        if (p.status === "error") {
            alert(p.msg || "Producto no encontrado");
            return;
        }

        const nameEl = document.getElementById("productName");
        const priceEl = document.getElementById("productPrice");
        const descEl = document.getElementById("productDescription");
        const stockEl = document.getElementById("productStock");
        const imgEl = detail.querySelector("img");

        if (nameEl) nameEl.textContent = p.nombreProducto;
        if (priceEl) priceEl.textContent = `$${p.precio} MXN`;
        if (descEl) descEl.textContent = p.descripcion || "";
        if (imgEl) {
            imgEl.src = p.imagen || "https://via.placeholder.com/400x300";
            imgEl.alt = p.nombreProducto;
        }
        if (stockEl) {
            if (p.cantidad > 0) {
                stockEl.textContent = "¡En stock!";
                stockEl.classList.remove("out");
            } else {
                stockEl.textContent = "Agotado";
                stockEl.classList.add("out");
            }
        }

        const btnAgregar = document.getElementById("btnAgregarCarrito");
        const btnComprar = document.getElementById("btnComprarAhora");

        if (btnAgregar) {
            btnAgregar.onclick = () => agregarCarrito(p.idProducto, 1);
        }
        if (btnComprar) {
            btnComprar.onclick = async () => {
                await agregarCarrito(p.idProducto, 1);
                window.location.href = "cart.html";
            };
        }

    } catch (e) {
        console.error(e);
        alert("Error cargando producto");
    }
}


// ===========================================================
//   CARRITO
// ===========================================================
async function agregarCarrito(idProducto, cantidad = 1) {
    try {
        let res = await fetch("/api/carrito/agregar", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: `idProducto=${encodeURIComponent(idProducto)}&cantidad=${encodeURIComponent(cantidad)}`
        });

        let json = await res.json();

        if (json.status === "ok") {
            alert("Producto agregado al carrito.");
        } else {
            alert(json.msg || "No se pudo agregar al carrito.");
        }

    } catch (e) {
        console.error(e);
        alert("Error al agregar al carrito");
    }
}

async function cargarCarrito() {
    const cont = document.getElementById("cartItems");
    if (!cont) return;

    try {
        let res = await fetch("/api/carrito/obtener");
        let data = await res.json();

        const totalEl = document.getElementById("cartTotal");
        const countEl = document.getElementById("cartItemsCount");

        cont.innerHTML = "";

        (data.productos || []).forEach(item => {
            const p = item.producto;
            const cantidad = item.cantidad;

            cont.innerHTML += `
                <div class="cart-item">
                    <img src="${p.imagen || 'https://via.placeholder.com/80x80'}" class="cart-img" alt="${p.nombreProducto}">
                    <div class="cart-info">
                        <strong>${p.nombreProducto}</strong><br>
                        ${p.descripcion || ""}
                    </div>
                    <div class="cart-qty">
                        <button onclick="cambiarCantidad('${p.idProducto}', ${cantidad - 1})">-</button>
                        <span>${cantidad}</span>
                        <button onclick="cambiarCantidad('${p.idProducto}', ${cantidad + 1})">+</button>
                    </div>
                    <div class="cart-price">
                        <strong>$${(p.precio * cantidad).toFixed(2)} MXN</strong><br>
                        <button onclick="eliminarCarrito('${p.idProducto}')">❌</button>
                    </div>
                </div>
            `;
        });

        if (totalEl) {
            totalEl.textContent = `$${(data.total || 0).toFixed(2)} MXN`;
        }
        if (countEl) {
            countEl.textContent = String((data.productos || []).length);
        }

    } catch (e) {
        console.error(e);
        alert("Error cargando carrito");
    }
}

async function cambiarCantidad(idProducto, cantidad) {
    if (cantidad <= 0) return;

    try {
        await fetch("/api/carrito/cambiarCantidad", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: `idProducto=${encodeURIComponent(idProducto)}&cantidad=${encodeURIComponent(cantidad)}`
        });

        await cargarCarrito();

    } catch (e) {
        console.error(e);
        alert("Error actualizando cantidad");
    }
}

async function eliminarCarrito(idProducto) {
    try {
        await fetch("/api/carrito/eliminar", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: `idProducto=${encodeURIComponent(idProducto)}`
        });

        await cargarCarrito();

    } catch (e) {
        console.error(e);
        alert("Error eliminando producto del carrito");
    }
}

async function vaciarCarrito() {
    try {
        await fetch("/api/carrito/vaciar", { method: "POST" });
        await cargarCarrito();
    } catch (e) {
        console.error(e);
        alert("Error al vaciar carrito");
    }
}


// ===========================================================
//   ADMIN: INVENTARIO
// ===========================================================
async function cargarInventario() {
    const grid = document.getElementById("inventarioGrid");
    if (!grid) return;

    // ya está protegido en backend, pero validamos también en front
    const ses = await obtenerSesion();
    if (ses.status !== "ok" || ses.tipoUsuario !== "Administrador") {
        alert("Solo un administrador puede ver el inventario.");
        window.location.href = "home.html";
        return;
    }

    try {
        let res = await fetch("/api/productos");
        let inventario = await res.json();

        grid.innerHTML = "";

        inventario.forEach(p => {
            grid.innerHTML += `
                <div class="product-card">
                    <img src="${p.imagen || 'https://via.placeholder.com/200x150'}" class="product-img">
                    <h3>${p.nombreProducto}</h3>
                    <p>Cantidad: ${p.cantidad}</p>
                    <p>Precio: $${p.precio} MXN</p>
                    <button onclick="eliminarProductoAdmin('${p.idProducto}')">Eliminar</button>
                </div>
            `;
        });

    } catch (e) {
        console.error(e);
        alert("Error cargando inventario");
    }
}

async function eliminarProductoAdmin(id) {
    try {
        await fetch("/api/admin/eliminarProducto", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: `id=${encodeURIComponent(id)}`
        });

        await cargarInventario();

    } catch (e) {
        console.error(e);
        alert("Error eliminando producto");
    }
}

async function agregarProductoAdmin() {
    const nombre = document.getElementById("nuevoNombre")?.value || "";
    const precio = document.getElementById("nuevoPrecio")?.value || "0";
    const cantidad = document.getElementById("nuevoCantidad")?.value || "0";
    const desc = document.getElementById("nuevoDescripcion")?.value || "";
    const img = document.getElementById("nuevoImagen")?.value || "";

    // si no hay campo para ID, lo generamos
    const idInput = document.getElementById("nuevoId");
    const id = idInput && idInput.value
        ? idInput.value
        : "P" + Date.now();

    try {
        await fetch("/api/admin/agregarProducto", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body:
                `nombre=${encodeURIComponent(nombre)}` +
                `&id=${encodeURIComponent(id)}` +
                `&precio=${encodeURIComponent(precio)}` +
                `&cantidad=${encodeURIComponent(cantidad)}` +
                `&descripcion=${encodeURIComponent(desc)}` +
                `&imagen=${encodeURIComponent(img)}`
        });

        // opcional: ocultar formulario y recargar inventario
        const form = document.getElementById("formAgregar");
        if (form) form.style.display = "none";

        await cargarInventario();

    } catch (e) {
        console.error(e);
        alert("Error agregando producto");
    }
}

// helpers para inventario.html (coinciden con los nombres del HTML)
function mostrarAgregarProducto() {
    const form = document.getElementById("formAgregar");
    if (!form) return;
    form.style.display = (form.style.display === "none" || form.style.display === "") ? "block" : "none";
}

function agregarProducto() {
    agregarProductoAdmin();
}


// ===========================================================
//   AUTO-INIT POR PÁGINA
// ===========================================================
document.addEventListener("DOMContentLoaded", async () => {
    const path = window.location.pathname;

    if (document.getElementById("productsGrid")) {
        await cargarProductos();
    }

    if (document.getElementById("productDetail")) {
        await cargarDetalleProducto();
    }

    if (document.getElementById("cartItems")) {
        // carrito solo para comprador
        await verificarAcceso("Comprador");
        await cargarCarrito();
    }

    if (path.endsWith("inventario.html")) {
        await verificarAcceso("Administrador");
        await cargarInventario();
    }
    
});

// ===========================================================
//   INVENTARIO: CRUD COMPLETO (Editar + Crear + Eliminar)
// ===========================================================

let editandoID = null;  // null = crear | string = editar

function limpiarFormulario() {
    editandoID = null;
    document.getElementById("formTitulo").innerText = "Nuevo Producto";

    document.getElementById("formId").value = "";
    document.getElementById("formNombre").value = "";
    document.getElementById("formPrecio").value = "";
    document.getElementById("formCantidad").value = "";
    document.getElementById("formImagen").value = "";
    document.getElementById("formDescripcion").value = "";
}

//   ADMIN: INVENTARIO
// ===========================================================
async function cargarInventario() {
    let ses = await obtenerSesion();
    if (!ses || ses.tipoUsuario !== "Administrador") return;

    let res = await fetch("/api/productos");
    let inventario = await res.json();

    let cont = document.getElementById("inventarioGrid");
    if (!cont) return;

    cont.innerHTML = "";

    inventario.forEach(p => {
        cont.innerHTML += `
            <div class="inv-card">
                <img src="${p.imagen}" alt="${p.nombreProducto}">
                <div class="inv-card-info">
                    <h3>${p.nombreProducto}</h3>
                    <p>Cantidad: ${p.cantidad}</p>
                    <p>Precio: $${p.precio} MXN</p>
                </div>
                <div class="inv-card-buttons">
                    <button class="btn-edit" onclick="redirEditarProducto('${p.idProducto}')">
                        Editar
                    </button>
                    <button class="btn-delete" onclick="eliminarProductoAdmin('${p.idProducto}')">
                        Eliminar
                    </button>
                </div>
            </div>
        `;
    });
}


async function editarProducto(id) {
    const res = await fetch(`/api/producto/${id}`);
    const p = await res.json();

    editandoID = id;

    document.getElementById("formTitulo").innerText = "Editar Producto";
    document.getElementById("formId").value = p.idProducto;
    document.getElementById("formNombre").value = p.nombreProducto;
    document.getElementById("formPrecio").value = p.precio;
    document.getElementById("formCantidad").value = p.cantidad;
    document.getElementById("formImagen").value = p.imagen;
    document.getElementById("formDescripcion").value = p.descripcion;
}

async function guardarProducto() {
    const id = document.getElementById("formId").value;
    const nombre = document.getElementById("formNombre").value;
    const precio = document.getElementById("formPrecio").value;
    const cantidad = document.getElementById("formCantidad").value;
    const imagen = document.getElementById("formImagen").value;
    const descripcion = document.getElementById("formDescripcion").value;

    let body =
        `id=${encodeURIComponent(id)}` +
        `&nombre=${encodeURIComponent(nombre)}` +
        `&precio=${encodeURIComponent(precio)}` +
        `&cantidad=${encodeURIComponent(cantidad)}` +
        `&imagen=${encodeURIComponent(imagen)}` +
        `&descripcion=${encodeURIComponent(descripcion)}`;

    if (editandoID) {
        // EDITAR
        await fetch("/api/admin/editarProducto", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body
        });
        alert("Producto actualizado.");
    } else {
        // NUEVO
        await fetch("/api/admin/agregarProducto", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body
        });
        alert("Producto agregado.");
    }

    limpiarFormulario();
    cargarInventario();
}

async function guardarProductoNuevo() {
    const id = document.getElementById("formId").value;
    const nombre = document.getElementById("formNombre").value;
    const precio = document.getElementById("formPrecio").value;
    const cantidad = document.getElementById("formCantidad").value;
    const imagen = document.getElementById("formImagen").value;
    const descripcion = document.getElementById("formDescripcion").value;

    let body =
        `id=${encodeURIComponent(id)}` +
        `&nombre=${encodeURIComponent(nombre)}` +
        `&precio=${encodeURIComponent(precio)}` +
        `&cantidad=${encodeURIComponent(cantidad)}` +
        `&imagen=${encodeURIComponent(imagen)}` +
        `&descripcion=${encodeURIComponent(descripcion)}`;

    await fetch("/api/admin/agregarProducto", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body
    });

    alert("Producto agregado.");
    window.location.href = "inventario.html";
}

// Redirigir a pantalla de edición de producto (vista admin)
function redirEditarProducto(idProducto) {
    // Tus vistas están bajo /view/
    window.location.href = `/view/editProduct.html?id=${idProducto}`;
}

document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("registerForm");

    if (!form) return; // Si no estamos en register.html, no hacer nada

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const nombre = e.target.nombre.value.trim();
        const direccion = e.target.direccion.value.trim();
        const correo = e.target.correo.value.trim();
        const password = e.target.password.value.trim();
        const password2 = e.target.password2.value.trim();

        if (!nombre || !direccion || !correo || !password) {
            alert("Por favor completa todos los campos.");
            return;
        }

        if (password !== password2) {
            alert("Las contraseñas no coinciden.");
            return;
        }

        // Spark Java NO recibe JSON aquí → usamos x-www-form-urlencoded
        const body = new URLSearchParams();
        body.append("nombre", nombre);
        body.append("direccion", direccion);
        body.append("correo", correo);
        body.append("password", password);

        try {
            const res = await fetch("/api/register", {
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: body.toString()
            });

            const json = await res.json();

            if (json.status === "ok") {
                alert("Registro exitoso.");
                window.location.href = "login.html";
            } else {
                alert(json.msg || "No se pudo registrar.");
            }
        } catch (e) {
            console.error(e);
            alert("Error conectando con el servidor.");
        }
    });
});


