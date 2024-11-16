const jwt = require("jsonwebtoken");

function authenticateToken(req, res, next) {
  const token = req.headers["authorization"]?.split(" ")[1];
  
  if (!token) {
    return res.status(401).json({
      msg: "Você precisa se autenticar para acessar essa página",
    });
  }

  jwt.verify(token, process.env.SECRET, (err, user) => {
    if (err) {
      console.error("Erro na verificação do token:", err); // Log do erro
      return res.status(403).json({
        msg: "Sua sessão expirou, por favor faça login novamente"
      });
    }

    // Armazenar usuário na requisição
    req.user = user;
    next();
  });
}

module.exports = authenticateToken;