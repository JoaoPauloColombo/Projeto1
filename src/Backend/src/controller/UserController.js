const User = require("../models/User");
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');

const UserController = {
  login: async (req, res) => {
    try {
      const { email, senha } = req.body;

      console.log(`Tentando fazer login com o email: ${email}`);

      // Busca o usuário pelo email
      const user = await User.findOne({ where: { email } });

      console.log(`Usuário encontrado: ${JSON.stringify(user)}`);

      // Verifica se o usuário existe
      if (!user) {
        return res.status(401).json({ msg: "Usuário não encontrado." });
      }

      // Verifica se a senha está correta
      const isPasswordValid = await bcrypt.compare(senha, user.senha);

      if (!isPasswordValid) {
        return res.status(401).json({ msg: "Senha incorreta." });
      }

      // Gera o token JWT
      const token = jwt.sign(
        { id: user.id, email: user.email, nome: user.nome }, // Incluindo o ID do usuário
        process.env.SECRET, // Certifique-se de que a variável de ambiente está definida
        { expiresIn: "1h" }
      );

      // Retorna o token ao cliente
      return res.status(200).json({ msg: "Login realizado", token });
    } catch (error) {
      console.error("Erro ao fazer login:", error);
      return res.status(500).json({ msg: "Erro ao fazer login." });
    }
  },

  create: async (req, res, usuarioDescriptografado) => {
    console.log('Corpo da requisição no controlador:', usuarioDescriptografado); // Adicione este log

    try {
      let { nome, senha, email } = usuarioDescriptografado;

      nome = nome.trim();
      senha = senha.trim();
      email = email.trim();

      console.log(`Dados recebidos: Nome: ${nome}, Email: ${email}, Senha: ${senha}`);

      if (!senha) {
        return res.status(400).json({ msg: "Senha não pode ser vazia." });
      }

      const saltRounds = 10;
      const encryptedSenha = await bcrypt.hash(senha, saltRounds);

      console.log(`Senha normal: ${senha}`);
      console.log(`Senha criptografada: ${encryptedSenha}`);

      const userCriado = await User.create({ nome, senha: encryptedSenha, email });

      return res.status(200).json({
        msg: "Usuário criado com sucesso!",
        user: userCriado,
      });
    } catch (error) {
      console.error("Erro ao criar usuário:", error);
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

      let encryptedSenha = userUpdate.senha;
      if (senha) {
        const saltRounds = 10;
        encryptedSenha = await bcrypt.hash(senha.trim(), saltRounds);
      }

      const updated = await userUpdate.update({
        nome,
        senha: encryptedSenha,
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
          msg: "Usuário não encontrado!",
        });
      }
      return res.status(200).json({
        msg: "Usuário encontrado",
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
          msg: "Usuário não encontrado",
        });
      }
      await userFinded.destroy();

      return res.status(200).json({
        msg: "Usuário deletado com sucesso",
      });
    } catch (error) {
      console.error(error);
      return res.status(500).json({ msg: "Acione o Suporte" });
    }
  },
};

module.exports = UserController;