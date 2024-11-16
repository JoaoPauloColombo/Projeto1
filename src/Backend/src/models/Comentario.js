const { DataTypes } = require("sequelize");
const sequelize = require("../config/config");
const Coordenada = require("./Coordenadas");
const User = require("./User");

const Comentario = sequelize.define('comentario', {
    descricao: {
        type: DataTypes.STRING,
        allowNull: false
    },
    nota: {
        type: DataTypes.INTEGER,
        allowNull: false
    },
    coordenadaId: {
        type: DataTypes.INTEGER,
        references: {
            model: Coordenada,
            key: 'id'
        },
        allowNull: false
    },
    userId: {
        type: DataTypes.INTEGER,
        references: {
            model: User,
            key: 'id' 
        },
        allowNull: false
    }
});

Comentario.belongsTo(Coordenada, { foreignKey: 'coordenadaId' });
Comentario.belongsTo(User, { foreignKey: 'userId' });

module.exports = Comentario;