const User = require("../models/User");
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs'); // Importando bcryptjs

const UserController = {
  login: async (req, res) => {
    try {
      const { email, senha } = req.body;

      // Buscar usuário pelo email
      const user = await User.findOne({ where: { email } });

      if (!user) {
        return res.status(401).json({ msg: "Usuário não encontrado." });
      }

      // Comparar a senha fornecida com a senha armazenada
      const isPasswordValid = await bcrypt.compare(senha, user.senha);

      if (!isPasswordValid) {
        return res.status(401).json({ msg: "Senha incorreta." });
      }

      // Gerar token JWT
      const token = jwt.sign({ email: user.email, nome: user.nome }, process.env.SECRET, { expiresIn: "1h" });

      return res.status(200).json({ msg: "Login realizado", token });
    } catch (error) {
      console.error("Erro ao fazer login:", error);
      return res.status(500).json({ msg: "Erro ao fazer login." });
    }
  },

  create: async (req, res) => {
    try {
      let { nome, senha, email } = req.body;

      // Remover espaços em branco desnecessários
      nome = nome.trim();
      senha = senha.trim();
      email = email.trim();

      // Log para verificar os dados recebidos
      console.log(`Dados recebidos: Nome: ${nome}, Email: ${email}, Senha: ${senha}`);

      // Verificar se a senha está vazia
      if (!senha) {
        return res.status(400).json({ msg: "Senha não pode ser vazia." });
      }

      // Criptografar a senha antes de salvar
      const saltRounds = 10; // Número de rounds para gerar o sal
      const encryptedSenha = await bcrypt.hash(senha, saltRounds);

      // Log para ver a senha normal e a senha criptografada
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

      // Criptografar a nova senha antes de atualizar, se fornecida
      let encryptedSenha = userUpdate.senha; // Manter a senha atual se nenhuma nova for fornecida
      if (senha) {
        const saltRounds = 10; // Número de rounds para gerar o sal
        encryptedSenha = await bcrypt.hash(senha.trim(), saltRounds);
      }

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