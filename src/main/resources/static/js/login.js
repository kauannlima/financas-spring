document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const formData = new FormData(this);
    const loginData = {
        email: formData.get("email"),
        senha: formData.get("senha")
    };

    fetch("/usuarios/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(loginData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Credenciais de login inválidas.");
        }
        return response.json();
    })
    .then(data => {
        const usuarioId = data.Id;
        localStorage.setItem('usuarioId', usuarioId);
        window.location.href = '/main.html';
    })
    .catch(error => {
        console.error("Erro ao fazer login:", error);
        // Aqui você pode exibir uma mensagem de erro para o usuário
        alert("Credenciais de login inválidas. Por favor, verifique seu email e senha.");
    });
});
