const Coordenadas = require("../models/Coordenadas");

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
      console.error(error);
      return res.status(500).json({ msg: "Acione o Suporte" });
    }
  },
  
  update: async (req, res) => {
    try {
      const { id } = req.params;
      const { latitude, longitude, nome } = req.body;

      const coordenadaUpdate = await Coordenadas.findByPk(id);

      if (coordenadaUpdate == null) {
        return res.status(404).json({
          msg: "Coordenada não encontrada",
        });
      }

      const updated = await coordenadaUpdate.update({
        latitude,
        longitude,
        nome
      });
      
      if (updated) {
        return res.status(200).json({
          msg: "Coordenada atualizada com sucesso!",
        });
      }

      return res.status(500).json({
        msg: "Erro ao atualizar coordenada"
      });
    } catch (error) {
      console.error(error);
      return res.status(500).json({ msg: "Acione o Suporte" });
    }
  },
  
  getAll: async (req, res) => {
    try {
      const coordenadas = await Coordenadas.findAll();
      return res.status(200).json({
        msg: "Coordenadas encontradas!",
        coordenadas,
      });
    } catch (error) {
      console.error(error);
      return res.status(500).json({ msg: "Acione o Suporte" });
    }
  },
  
  getOne: async (req, res) => {
    try {
      const { id } = req.params;

      const coordenadaEncontrada = await Coordenadas.findByPk(id);

      if (coordenadaEncontrada == null) {
        return res.status(404).json({
          msg: "Coordenada não encontrada!",
        });
      }
      return res.status(200).json({
        msg: "Coordenada encontrada",
        coordenada: coordenadaEncontrada,
      });
    } catch (error) {
      console.error(error);
      return res.status(500).json({ msg: "Acione o Suporte" });
    }
  },
  
  delete: async (req, res) => {
    try {
      const { id } = req.params;

      const coordenadaEncontrada = await Coordenadas.findByPk(id);

      if (coordenadaEncontrada == null) {
        return res.status(404).json({
          msg: "Coordenada não encontrada",
        });
      }
      await coordenadaEncontrada.destroy();

      return res.status(200).json({
        msg: "Coordenada deletada com sucesso",
      });
    } catch (error) {
      console.error(error);
      return res.status(500).json({ msg: "Acione o Suporte" });
    }
  },
};

module.exports = CoordenadasController;