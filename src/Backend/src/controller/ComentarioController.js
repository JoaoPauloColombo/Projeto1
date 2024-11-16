const Comentario = require("../models/Comentario");
const User = require("../models/User");

const ComentarioController = {
  create: async (req, res) => {
    try {
      const { descricao, nota } = req.body;
      const userId = req.user.id;

      const comentarioCriado = await Comentario.create({ descricao, nota, userId });

      return res.status(200).json({
        msg: "Comentario criado com sucesso!",
        comentario: comentarioCriado,
      });
    } catch (error) {
      console.error(error);
      return res.status(500).json({ msg: "Acione o Suporte" });
    }
  },
  
  update: async (req, res) => {
    try {
      const { id } = req.params;
      const { descricao, nota } = req.body;

      const comentarioUpdate = await Comentario.findByPk(id);

      if (comentarioUpdate == null) {
        return res.status(404).json({
          msg: "Comentario nao encontrado",
        });
      }

      const updated = await comentarioUpdate.update({
        descricao, 
        nota
      });
      if (updated) {
        return res.status(200).json({
          msg: "Comentario atualizado com sucesso!",
        });
      }
      return res.status(500).json({
        msg: "Erro ao atualizar comentario"
      });
    } catch (error) {
      console.error(error);
      return res.status(500).json({ msg: "Acione o Suporte" });
    }
  },
  
  getAll: async (req, res) => {
    try {
      const comentarios = await Comentario.findAll({
        include: [{
          model: User,
          attributes: ['nome', 'email']
        }]
      });
      return res.status(200).json({
        msg: "Comentarios Encontrados!",
        comentarios,
      });
    } catch (error) {
      console.error(error);
      return res.status(500).json({ msg: "Acione o Suporte" });
    }
  },
  
  getOne: async (req, res) => {
    try {
      const { id } = req.params;

      const comentarioEncontrado = await Comentario.findByPk(id, {
        include: [{
          model: User,
          attributes: ['nome', 'email']
        }]
      });

      if (comentarioEncontrado == null) {
        return res.status(404).json({
          msg: "Comentario nao encontrado!",
        });
      }
      return res.status(200).json({
        msg: "Comentario Encontrado",
        comentario: comentarioEncontrado,
      });
    } catch (error) {
      console.error(error);
      return res.status(500).json({ msg: "Acione o Suporte" });
    }
  },
  
  delete: async (req, res) => {
    try {
      const { id } = req.params;

      const comentarioFinded = await Comentario.findByPk(id);

      if (comentarioFinded == null) {
        return res.status(404).json({
          msg: "Comentario nao encontrado",
        });
      }
      await comentarioFinded.destroy();

      return res.status(200).json({
        msg: "Comentario deletado com sucesso",
      });
    } catch (error) {
      console.error(error);
      return res.status(500).json({ msg: "Acione o Suporte" });
    }
  },
};

module.exports = ComentarioController;