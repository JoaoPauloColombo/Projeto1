const jwt = require('jsonwebtoken');

const authenticateToken = (req, res, next) => {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];

    // Log do cabeçalho de autenticação e do token extraído
    console.log("Auth Header:", authHeader); 
    console.log("Token:", token); 

    // Verifica se o token está presente
    if (!token) {
        console.log("Token não fornecido."); // Log da falta de token
        return res.status(401).json({ msg: "Você precisa se autenticar para acessar essa página" });
    }

    // Verifica o token
    jwt.verify(token, process.env.SECRET, (err, user) => { // Use a mesma variável aqui
        if (err) {
            console.log("Erro na verificação do token:", err.message); // Log do erro
            return res.status(403).json({ msg: "Token inválido", error: err.message });
        }
        
        // Log do usuário autenticado
        console.log("Usuário autenticado:", user); // Log do usuário autenticado
        req.user = user; // Salva o usuário no objeto de requisição
        next();
    });
};

module.exports = authenticateToken;