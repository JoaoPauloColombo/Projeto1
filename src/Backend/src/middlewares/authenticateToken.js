const jwt = require('jsonwebtoken');

const authenticateToken = (req, res, next) => {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1]; // Pega o token após 'Bearer'

    if (!token) {
        return res.status(401).json({ msg: "Você precisa se autenticar para acessar essa página" });
    }

    jwt.verify(token, process.env.TOKEN_SECRET, (err, user) => {
        if (err) {
            return res.status(403).json({ msg: "Token inválido" });
        }
        req.user = user;
        next();
    });
};

module.exports = authenticateToken;