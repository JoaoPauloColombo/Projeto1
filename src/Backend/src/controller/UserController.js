const User = require("../models/User");
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const { Op } = require('sequelize');

const UserController = {
  login: async (req, res) => {
    try {
      const { email, senha } = req.body;

      const user = await User.findOne({ where: { email } });

      if (!user) {
        return res.status(401).json({ msg: "Usuário não encontrado." });
      }

      const isPasswordValid = await bcrypt.compare(senha, user.senha);

      if (!isPasswordValid) {
        return res.status(401).json({ msg: "Senha incorreta." });
      }

      const token = jwt.sign({ id: user.id, email: user.email, nome: user.nome }, process.env.SECRET, { expiresIn: "1h" });

      return res.status(200).json({ msg: "Login realizado", token });
    } catch (error) {
      console.error("Erro ao fazer login:", error);
      return res.status(500).json({ msg: "Erro ao fazer login." });
    }
  },

  create: async (req, res) => {
    try {
      let { nome, senha, email } = req.body;

      nome = nome.trim();
      senha = senha.trim();
      email = email.trim();

      const existingUser = await User.findOne({ where: { email } });
      if (existingUser) {
        return res.status(400).json({ msg: "Email já está em uso." });
      }

      const saltRounds = 10;
      const encryptedSenha = await bcrypt.hash(senha, saltRounds);

      const userCriado = await User.create({ nome, senha: encryptedSenha, email });

      return res.status(201).json({
        msg: "Usuário criado com sucesso!",
        user: {
          id: userCriado.id,
          nome: userCriado.nome,
          email: userCriado.email,
        },
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

      const userUpdate = await User.findByPk(id);

      if (userUpdate == null) {
        return res.status(404).json({
          msg: "Usuário não encontrado",
        });
      }
      if (senha) {
        const saltRounds = 10;
        encryptedSenha = await bcrypt.hash(senha.trim(), saltRounds);
      }

      
      const existingUser = await User.findOne({
        where: {
          email,
          id: { [Op.ne]: id } 
        }
      });

      if (existingUser) {
        return res.status(400).json({ msg: "Email já está em uso." });
      }

      const updated = await userUpdate.update({
        nome,
        senha: encryptedSenha,
        email,
      });

      if (updated) {
        return res.status(200).json({
          msg: "Usuário atualizado com sucesso!",
          usuario: {
            id: userUpdate.id,
            nome: userUpdate.nome,
            email: userUpdate.email,
          },
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
      const usuarios = await User.findAll({
        attributes: { exclude: ['senha'] }
      });
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

      const usuarioEncontrado = await User.findByPk(id, {
        attributes: { exclude: ['senha'] }
      });

      if (usuarioEncontrado == null) {
        return res.status(404).json({
          msg: "Usuário não encontrado!",
        });
      }
      return res.status(200).json({
        msg: "Usuário encontrado",
        usuario: {
          id: usuarioEncontrado.id,
          nome: usuarioEncontrado.nome,
          email: usuarioEncontrado.email,
        },
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
