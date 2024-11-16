// src/models/associations.js
const Coordenadas = require('./Coordenadas');
const Comentario = require('./Comentario');

// Definindo as associações
Coordenadas.hasMany(Comentario, { foreignKey: 'coordenadaId', as: 'comentarios' });
Comentario.belongsTo(Coordenadas, { foreignKey: 'coordenadaId' });