const Coordenadas = require('./Coordenadas');
const Comentario = require('./Comentario');
const User = require('./User');

Coordenadas.hasMany(Comentario, { foreignKey: 'coordenadaId', as: 'comentarios' });
Comentario.belongsTo(Coordenadas, { foreignKey: 'coordenadaId' });

User .hasMany(Comentario, { foreignKey: 'userId', as: 'comentarios' });
Comentario.belongsTo(User, { foreignKey: 'userId' });

module.exports = { Coordenadas, Comentario, User };