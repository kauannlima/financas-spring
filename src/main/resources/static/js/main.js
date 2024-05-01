const usuarioId = localStorage.getItem('usuarioId');

function getSaldoUsuario() {
    fetch(`/usuarios/${usuarioId}/saldo`)
        .then(response => response.json())
        .then(data => {
            const saldoElement = document.getElementById("saldo");
            saldoElement.textContent = "R$ " + data.toFixed(2);
            saldoElement.classList.toggle("negativo", data < 0); 
         })
        .catch(error => console.error("Erro ao obter saldo:", error));
}


    function getReceitas() {
        return fetch(`usuarios/${usuarioId}/receitas`)
            .then(response => response.json())
            .catch(error => {
                console.error("Erro ao obter receitas:", error);
                return [];
            });
    }

    function getDespesas() {
        return fetch(`usuarios/${usuarioId}/despesas`)
            .then(response => response.json())
            .catch(error => {
                console.error("Erro ao obter despesas:", error);
                return [];
            });
    }

    function exibirReceitas() {
        getReceitas()
            .then(receitas => {
                const listaReceitas = document.getElementById("listaReceitas");
                listaReceitas.innerHTML = "";
                receitas.forEach(receita => {
                    const div = document.createElement("div");
                    div.classList.add("itens");
    
                    const descricao = document.createElement("span");
                    descricao.classList.add("spanDescricao")
                    descricao.textContent = `${receita.descricao}`;
                    div.appendChild(descricao);

                    const valor = document.createElement("span");
                    valor.classList.add("spanValor")
                    valor.textContent = `R$ ${receita.valor.toFixed(2)}`;
                    div.appendChild(valor);

                    const tipo = document.createElement("span");
                    tipo.classList.add("gg-arrow-up-o")
                    tipo.textContent = ``;
                    div.appendChild(tipo);
    
                    const btnExcluir = document.createElement("button");
                    btnExcluir.classList.add("gg-trash");
                    btnExcluir.textContent = "";
                    btnExcluir.addEventListener("click", () => deletarReceita(receita.id));
                    div.appendChild(btnExcluir);
    
                    listaReceitas.appendChild(div);
                });
            });
    }
    
    
    function exibirDespesas() {
        getDespesas()
            .then(despesas => {
                const listaDespesas = document.getElementById("listaDespesas");
                listaDespesas.innerHTML = "";
                despesas.forEach(despesa => {
                    const div = document.createElement("div"); // Criar uma div para cada despesa
                    div.classList.add("itens");
    
                    const descricao = document.createElement("span");
                    descricao.textContent = despesa.descricao;
                    descricao.classList.add("spanDescricao")
                    div.appendChild(descricao);
    
                    const valor = document.createElement("span");
                    valor.textContent = `R$ ${despesa.valor.toFixed(2)}`;
                    valor.classList.add("spanValor")
                    div.appendChild(valor);

                    const tipo = document.createElement("span");
                    tipo.classList.add("gg-arrow-down-o")
                    tipo.textContent = ``;
                    div.appendChild(tipo);

                    const btnExcluir = document.createElement("button");
                    btnExcluir.classList.add("gg-trash");
                    btnExcluir.textContent = "";
                    btnExcluir.addEventListener("click", () => deletarDespesa(despesa.id));
                    div.appendChild(btnExcluir);
    
                    listaDespesas.appendChild(div);
                });
            });
    }
    

    function deletarReceita(receitaId) {
        fetch(`/receitas/${usuarioId}/${receitaId}`, {
            method: "DELETE"
        })
        .then(response => {
            if (response.ok) {
                getSaldoUsuario();
                exibirReceitas();
                calcularTotalReceitasEDespesas();            
            } else {
                console.error("Erro ao excluir receita");
            }
        })
        .catch(error => console.error("Erro ao excluir receita:", error));
    }

    function deletarDespesa(despesaId) {
        fetch(`/despesas/${usuarioId}/${despesaId}`, {
            method: "DELETE"
        })
        .then(response => {
            if (response.ok) {
                getSaldoUsuario();
                exibirDespesas();
                calcularTotalReceitasEDespesas();
            } else {
                console.error("Erro ao excluir despesa");
            }
        })
        .catch(error => console.error("Erro ao excluir despesa:", error));
    }

    function adicionarReceita(descricao, valor) {
        const movimento = {
            descricao: descricao,
            valor: valor
        };
    
        fetch(`/receitas/${usuarioId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(movimento)
        })
        .then(response => {
            if (response.ok) {
                getSaldoUsuario();
                exibirReceitas();
                calcularTotalReceitasEDespesas();
                document.getElementById("descricaoMovimento").value = "";
                document.getElementById("valorMovimento").value = "";
            } else {
                console.error("Erro ao adicionar receita");
            }
        })
        .catch(error => console.error("Erro ao adicionar receita:", error));
    }
    
    function adicionarDespesa(descricao, valor) {
        const movimento = {
            descricao: descricao,
            valor: valor
        };
    
        fetch(`/despesas/${usuarioId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(movimento)
        })
        .then(response => {
            if (response.ok) {
                getSaldoUsuario();
                exibirDespesas();
                calcularTotalReceitasEDespesas();
   
                document.getElementById("descricaoMovimento").value = "";
                document.getElementById("valorMovimento").value = "";
            } else {
                console.error("Erro ao adicionar despesa");
            }
        })
        .catch(error => console.error("Erro ao adicionar despesa:", error));
    }

    function calcularTotalReceitasEDespesas() {
        Promise.all([getReceitas(), getDespesas()])
            .then(([receitas, despesas]) => {
                const totalReceitas = receitas.reduce((acc, receita) => acc + receita.valor, 0);
                const totalDespesas = despesas.reduce((acc, despesa) => acc + despesa.valor, 0);
    
                const saldoReceitasElement = document.getElementById("receitas");
                saldoReceitasElement.textContent = `R$ ${totalReceitas.toFixed(2)}`;
    
                const saldoDespesasElement = document.getElementById("despesas");
                saldoDespesasElement.textContent = `R$ ${totalDespesas.toFixed(2)}`;

            })
            .catch(error => console.error("Erro ao calcular totais:", error));
    }

    function alertIfNegativo(){
        const saldo = document.getElementById("saldo");
        if(saldo <0){
alert("Atenção! Seu saldo está negativo. Tome cuidado com suas finanças.")
        }
    }
    
    document.addEventListener("DOMContentLoaded", function() {
        getSaldoUsuario();
        exibirReceitas();
        exibirDespesas();
        calcularTotalReceitasEDespesas();
    
        document.getElementById("formAdicionarMovimento").addEventListener("submit", function(event) {
            event.preventDefault();
            const tipoMovimento = document.getElementById("tipoMovimento").value;
            const descricao = document.getElementById("descricaoMovimento").value;
            const valor = parseFloat(document.getElementById("valorMovimento").value);
    
            if (tipoMovimento === "receita") {
                adicionarReceita(descricao, valor);
            } else {
                adicionarDespesa(descricao, valor);
            }
        });
    });
    
