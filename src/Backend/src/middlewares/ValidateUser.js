// Nome, email e senha
const validateUser  = (req, res, next) => {
  console.log('Corpo da requisição no middleware:', req.body); // Log para depuração

  // Verifica se req.body é um objeto e não está vazio
  if (!req.body || typeof req.body !== 'object' || Object.keys(req.body).length === 0) {
    return res.status(400).json({
      msg: "Corpo da requisição não pode estar vazio.",
    });
  }

  const { nome, email, senha } = req.body;

  if (!nome) {
    return res.status(400).json({
      msg: "O campo 'nome' é obrigatório",
    });
  }

  if (!email) {
    return res.status(400).json({
      msg: "O campo 'email' é obrigatório",
    });
  }

  if (!senha) {
    return res.status(400).json({
      msg: "O campo 'senha' é obrigatório",
    });
  }

  return next();
};

const validateUserId = (req, res, next) => {
  const { id } = req.params;

  if (!id) {
    return res.status(400).json({
      msg: "Parâmetro faltando",
    });
  }

  return next();
};

module.exports = { validateUser , validateUserId };