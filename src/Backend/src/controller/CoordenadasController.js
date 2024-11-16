const Coordenadas = require("../models/Coordenadas");
const Comentario = require("../models/Comentario");

const CoordenadasController = {
  create: async (req, res) => {
    try {
      const { latitude, longitude, nome } = req.body;

      const coordenadaCriada = await Coordenadas.create({ latitude, longitude, nome });

      return res.status(201).json({
        msg: "Coordenada criada com sucesso!",
        coordenada: coordenadaCriada,
      });
    } catch (error) {
      console.error("Erro ao criar coordenada:", error);
      return res.status(500).json({ msg: "Erro ao criar coordenada. Acione o Suporte." });
    }
  },
  
  update: async (req, res) => {
    try {
      const { id } = req.params;
      const { latitude, longitude, nome } = req.body;

      const coordenadaUpdate = await Coordenadas.findByPk(id);

      if (!coordenadaUpdate) {
        return res.status(404).json({
          msg: "Coordenada não encontrada",
        });
      }

      await coordenadaUpdate.update({ latitude, longitude, nome });
      
      return res.status(200).json({
        msg: "Coordenada atualizada com sucesso!",
        coordenada: coordenadaUpdate,
      });
    } catch (error) {
      console.error("Erro ao atualizar coordenada:", error);
      return res.status(500).json({ msg: "Erro ao atualizar coordenada. Acione o Suporte." });
    }
  },
  
  getAll: async (req, res) => {
    try {
      const coordenadas = await Coordenadas.findAll({
        include: [{
          model: Comentario,
          as: 'comentarios', // Certifique-se de que o alias está correto
          attributes: ['descricao', 'nota', 'userId'],
        }]
      });
      return res.status(200).json({
        msg: "Coordenadas encontradas!",
        coordenadas,
      });
    } catch (error) {
      console.error("Erro ao buscar coordenadas:", error);
      return res.status(500).json({ msg: "Erro ao buscar coordenadas. Acione o Suporte." });
    }
  },
  
  getOne: async (req, res) => {
    try {
      const { id } = req.params;

      const coordenadaEncontrada = await Coordenadas.findByPk(id, {
        include: [{
          model: Comentario,
          as: 'comentarios', // Certifique-se de que o alias está correto
          attributes: ['descricao', 'nota', 'userId'],
        }]
      });

      if (!coordenadaEncontrada) {
        return res.status(404).json({
          msg: "Coordenada não encontrada!",
        });
      }
      return res.status(200).json({
        msg: "Coordenada encontrada",
        coordenada: coordenadaEncontrada,
      });
    } catch (error) {
      console.error("Erro ao buscar coordenada:", error);
      return res.status(500).json({ msg: "Erro ao buscar coordenada. Acione o Suporte." });
    }
  },
  
  delete: async (req, res) => {
    try {
      const { id } = req.params;

      const coordenadaEncontrada = await Coordenadas.findByPk(id);

      if (!coordenadaEncontrada) {
        return res.status(404).json({
          msg: "Coordenada não encontrada",
        });
      }
      await coordenadaEncontrada.destroy();

      return res.status(200).json({
        msg: "Coordenada deletada com sucesso",
      });
    } catch (error) {
      console.error("Erro ao deletar coordenada:", error);
      return res.status(500).json({ msg: "Erro ao deletar coordenada. Acione o Suporte." });
    }
  },
};

module.exports = CoordenadasController;