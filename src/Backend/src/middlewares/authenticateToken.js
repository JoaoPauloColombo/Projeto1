const jwt = require("jsonwebtoken");

function authenticateToken(req, res, next) {
  // Obtém o token do cabeçalho Authorization
  const token = req.headers["authorization"]?.split(" ")[1];
  
  // Verifica se o token está presente
  if (!token) {
    return res.status(401).json({
      msg: "Você precisa se autenticar para acessar essa página",
    });
  }

  // Verifica o token usando a chave secreta
  jwt.verify(token, process.env.SECRET, (err, user) => {
    if (err) {
        console.error("Erro na verificação do token:", err);
        return res.status(403).json({
            msg: "Sua sessão expirou, por favor faça login novamente"
        });
    }

    // Se o token for válido, atribui o usuário a req.user
    console.log("Usuário autenticado:", user);
    req.user = user; // Aqui você deve garantir que 'user' contém o id do usuário
    next(); // Chama o próximo middleware ou rota
  });
}

module.exports = authenticateToken;