// src/models/Comentario.js
const { DataTypes } = require("sequelize");
const sequelize = require("../config/config");

const Comentario = sequelize.define('Comentario', {
    id: {
        type: DataTypes.INTEGER,
        autoIncrement: true,
        primaryKey: true,
        allowNull: false 
    },
    descricao: {
        type: DataTypes.STRING,
        allowNull: false
    },
    nota: {
        type: DataTypes.INTEGER,
        allowNull: false
    },
    userId: {
        type: DataTypes.INTEGER,
        allowNull: false,
        references: {
            model: 'users',
            key: 'id'
        }
    },
    coordenadaId: {
        type: DataTypes.INTEGER,
        allowNull: false,
        references: {
            model: 'coordenadas',
            key: 'id'
        }
    }
}, {
    timestamps: true,
    tableName: 'comentarios'
});

// Definindo associações
Comentario.associate = (models) => {
    Comentario.belongsTo(models.User, { foreignKey: 'userId' });
    Comentario.belongsTo(models.Coordenada, { foreignKey: 'coordenadaId' });
};

module.exports = Comentario;