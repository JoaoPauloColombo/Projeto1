const Comentario = require("../models/Comentario");
const User = require("../models/User");

const ComentarioController = {
  create: async (req, res) => {
    try {
      if (!req.user || !req.user.id) {
        return res.status(401).json({ msg: "Usuário não autenticado." });
      }
  
      console.log("ID do usuário:", req.user.id);
      const { descricao, nota } = req.body;
      const userId = req.user.id;
  
      const comentarioCriado = await Comentario.create({ descricao, nota, userId });
  
      return res.status(201).json({
        msg: "Comentário criado com sucesso!",
        comentario: comentarioCriado,
      });
    } catch (error) {
      console.error("Erro ao criar comentário:", error);
      return res.status(500).json({ 
        msg: "Erro interno ao criar comentário. Acione o suporte.", 
        error: error.message 
      });
    }
  },
  
  update: async (req, res) => {
    try {
      const { id } = req.params;
      const { descricao, nota } = req.body;

      const comentarioUpdate = await Comentario.findByPk(id);

      if (!comentarioUpdate) {
        return res.status(404).json({
          msg: "Erro: Comentário não encontrado.",
        });
      }

      // Atualiza apenas os campos que foram fornecidos
      const updated = await comentarioUpdate.update({
        descricao: descricao !== undefined ? descricao : comentarioUpdate.descricao,
        nota: nota !== undefined ? nota : comentarioUpdate.nota,
      });

      return res.status(200).json({
        msg: "Comentário atualizado com sucesso!",
        comentario: updated,
      });
    } catch (error) {
      console.error("Erro ao atualizar comentário:", error);
      return res.status(500).json({ 
        msg: "Erro interno ao atualizar comentário. Acione o suporte.", 
        error: error.message 
      });
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
        msg: "Comentários encontrados!",
        comentarios,
      });
    } catch (error) {
      console.error("Erro ao buscar comentários:", error);
      return res.status(500).json({ 
        msg: "Erro interno ao buscar comentários. Acione o suporte.", 
        error: error.message 
      });
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

      if (!comentarioEncontrado) {
        return res.status(404).json({
          msg: "Erro: Comentário não encontrado!",
        });
      }
      return res.status(200).json({
        msg: "Comentário encontrado.",
        comentario: comentarioEncontrado,
      });
    } catch (error) {
      console.error("Erro ao buscar comentário:", error);
      return res.status(500).json({ 
        msg: "Erro interno ao buscar comentário. Acione o suporte.", 
        error: error.message 
      });
    }
  },
  
  delete: async (req, res) => {
    try {
      const { id } = req.params;

      const comentarioFinded = await Comentario.findByPk(id);

      if (!comentarioFinded) {
        return res.status(404).json({
          msg: "Erro: Comentário não encontrado.",
        });
      }
      await comentarioFinded.destroy();

      return res.status(200).json({
        msg: "Comentário deletado com sucesso.",
      });
    } catch (error) {
      console.error("Erro ao deletar comentário:", error);
      return res.status(500).json({ 
        msg: "Erro interno ao deletar comentário. Acione o suporte.", 
        error: error.message 
      });
    }
  },
};

module.exports = ComentarioController;