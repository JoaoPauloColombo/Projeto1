// src/models/associations.js
const Coordenadas = require('./Coordenadas');
const Comentario = require('./Comentario');

// Exemplo de associação
Coordenadas.hasMany(Comentario, { foreignKey: 'coordenadaId' });
Comentario.belongsTo(Coordenadas, { foreignKey: 'coordenadaId' });