const validateUser  = (req, res, next) => {
  const { nome, email, senha } = req.body;

  if (!nome || typeof nome !== 'string') {
    return res.status(400).json({
      msg: "O campo 'nome' é obrigatório e deve ser uma string.",
    });
  }

  if (!email || typeof email !== 'string') {
    return res.status(400).json({
      msg: "O campo 'email' é obrigatório e deve ser uma string.",
    });
  }

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email)) {
    return res.status(400).json({
      msg: "O campo 'email' deve ser um email válido.",
    });
  }

  if (!senha || typeof senha !== 'string') {
    return res.status(400).json({
      msg: "O campo 'senha' é obrigatório e deve ser uma string.",
    });
  }

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
      return res.status(400).json({ msg: "Parâmetro 'id' faltando." });
  }
  if (isNaN(id) || Number(id) <= 0) {
      return res.status(400).json({ msg: "Parâmetro 'id' deve ser um número positivo." });
  }

  return next();
};

module.exports = { validateUser, validateUserId };