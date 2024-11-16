const validateUser = (req, res, next) => {
  const { nome, email, senha } = req.body;

  console.log('Valor do campo "nome":', nome);

  if (!nome) {
    return res.status(400).json({
      msg: "O campo 'nome' é obrigatório.",
    });
  }

  if (!email) {
    return res.status(400).json({
      msg: "O campo 'email' é obrigatório.",
    });
  }

  // Verificação de formato de email
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email)) {
    return res.status(400).json({
      msg: "O campo 'email' deve ser um email válido.",
    });
  }

  if (!senha) {
    return res.status(400).json({
      msg: "O campo 'senha' é obrigatório.",
    });
  }

  // Verificação de comprimento da senha
  if (senha.length < 6) {
    return res.status(400).json({
      msg: "A senha deve ter pelo menos 6 caracteres.",
    });
  }

  return next();
};

const validateUserId = (req, res, next) => {
  const { id } = req.params;

  if (!id) {
    return res.status(400).json({
      msg: "Parâmetro 'id' faltando.",
    });
  }

  return next();
};

module.exports = { validateUser, validateUserId };