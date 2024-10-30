const User = require("../models/User");
const jwt = require('jsonwebtoken');

// Funções de criptografia
const encrypt = (text, shift) => {
  return text.split('').map(char => {
    if (/[a-zA-Z]/.test(char)) {
      const code = char.charCodeAt(0);
      const base = code >= 97 ? 97 : 65; // 'a' ou 'A'
      return String.fromCharCode(((code - base + shift) % 26) + base);
    }
    return char;
  }).join('');
};

const decrypt = (text, shift) => {
  return encrypt(text, 26 - shift);
};

const UserController = {
  login: async (req, res) => {
    try {
      const { email, senha } = req.body;

      // Buscar usuário
      const user = await User.findOne({ where: { email } });

      if (!user) {
        return res.status(401).json({ msg: "Usuário não encontrado." });
      }

      // Descriptografar senha do banco de dados
      const shift = user.senha.length; // Usando o comprimento da senha como deslocamento
      const decryptedSenha = decrypt(user.senha, shift);

      // Comparar senha
      if (decryptedSenha !== senha) {
        return res.status(401).json({ msg: "Senha incorreta." });
      }

      // Gerar token
      const token = jwt.sign({ email: user.email, nome: user.nome }, process.env.SECRET, { expiresIn: "1h" });

      return res.status(200).json({ msg: "Login realizado", token });
    } catch (error) {
      console.error("Erro ao fazer login:", error);
      return res.status(500).json({ msg: "Erro ao fazer login." });
    }
  },

  create: async (req, res) => {
    try {
      const { nome, senha, email } = req.body;

      // Criptografar a senha antes de salvar
      const shift = senha.length; // Usando o comprimento da senha como deslocamento
      const encryptedSenha = encrypt(senha, shift);

      const userCriado = await User.create({ nome, senha: encryptedSenha, email });

      return res.status(200).json({
        msg: "Usuário criado com sucesso!",
        user: userCriado,
      });
    } catch (error) {
      console.error(error);
      return res.status(500).json({ msg: "Acione o Suporte" });
    }
  },

  update: async (req, res) => {
    try {
      const { id } = req.params;
      const { nome, senha, email } = req.body;

      console.log({ id });
      console.log({ nome, senha, email });

      const userUpdate = await User.findByPk(id);

      if (userUpdate == null) {
        return res.status(404).json({
          msg: "Usuário não encontrado",
        });
      }

      // Criptografar a nova senha antes de atualizar
      const shift = senha.length; // Usando o comprimento da nova senha como deslocamento
      const encryptedSenha = encrypt(senha, shift);

      const updated = await userUpdate.update({
        nome,
        senha: encryptedSenha, // Atualizando a senha criptografada
        email,
      });

      if (updated) {
        return res.status(200).json({
          msg: "Usuário atualizado com sucesso!",
        });
      }
      return res.status(500).json({
        msg: "Erro ao atualizar usuário",
      });
    } catch (error) {
      console.error(error);
      return res.status(500).json({ msg: "Acione o Suporte" });
    }
  },

  getAll: async (req, res) => {
    try {
      const usuarios = await User.findAll();
      return res.status(200).json({
        msg: "Usuários Encontrados!",
        usuarios,
      });
    } catch (error) {
      console.error(error);
      return res.status(500).json({ msg: "Acione o Suporte" });
    }
  },
  getOne: async (req, res) => {
    try {
      const { id } = req.params;

      const usuarioEncontrado = await User.findByPk(id);

      if (usuarioEncontrado == null) {
        return res.status(404).json({
          msg: "Usuario nao encontrado!",
        });
      }
      return res.status(200).json({
        msg: "Usuario Encontrados",
        usuario: usuarioEncontrado,
      });
    } catch (error) {
      console.error(error);
      return res.status(500).json({ msg: "Acione o Suporte" });
    }
  },
  delete: async (req, res) => {
    try {
      const { id } = req.params;

      const userFinded = await User.findByPk(id);

      if (userFinded == null) {
        return res.status(404).json({
          msg: "user nao encontrado",
        });
      }
      await userFinded.destroy();

      return res.status(200).json({
        msg: "Usuario deletado com sucesso",
      });
    } catch (error) {
      console.error(error);
      return res.status(500).json({ msg: "Acione o Suporte" });
    }
  },
};

module.exports = UserController;
