// ---------------------------
// LOGIN
// ---------------------------
if (document.getElementById("loginForm")) {

    document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const correo = document.querySelector("input[name='identificador']").value.trim();
    const password = document.querySelector("input[name='password']").value.trim();

    console.log("Correo capturado:", correo);  // Agrega esto
    console.log("Password capturada:", password);  // Agrega esto

    if (correo === "" || password === "") {
        alert("Datos incompletos");
        return;
    }

        try {
            let res = await fetch("/api/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: `identificador=${encodeURIComponent(correo)}&password=${encodeURIComponent(password)}`
            });

            if (!res.ok) {
                throw new Error(`HTTP error! status: ${res.status}`);
            }

            let json = await res.json();

            if (json.status === "ok") {
                window.location.href = "home.html";
            } else {
                alert(json.msg || "Error al iniciar sesión");
            }
        } catch (e) {
            console.error(e);
            alert("Error de conexión o respuesta inválida: " + e.message);
        }
    });
}


// LISTAR PRODUCTOS EN HOME
if (document.getElementById("productsGrid")) {

    async function cargarProductos() {
        try {
            let res = await fetch("/api/productos");

            if (!res.ok) {
                throw new Error(`HTTP error! status: ${res.status}`);
            }

            let productos = await res.json();

            let grid = document.getElementById("productsGrid");
            grid.innerHTML = ""; // limpiar contenido estático

            productos.forEach(p => {
                grid.innerHTML += `
                    <article class="product-card" onclick="verProducto('${p.idProducto}')">
                        <img src="https://via.placeholder.com/200x150">
                        <div class="product-name">${p.nombreProducto}</div>
                        <div class="product-price">$${p.precio} MXN</div>
                    </article>
                `;
            });
        } catch (e) {
            console.error(e);
            alert("Error cargando productos: " + e.message);
        }
    }

    cargarProductos();
}

// Navegar al detalle (por ahora solo cambia de página)
function verProducto(id) {
    window.location.href = "product.html?id=" + id;
}