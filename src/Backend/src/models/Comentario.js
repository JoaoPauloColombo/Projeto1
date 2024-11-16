// src/models/Comentario.js
const { DataTypes } = require("sequelize");
const sequelize = require("../config/config");

const Comentario = sequelize.define('comentario', {
    id: {
        type: DataTypes.INTEGER,
        autoIncrement: true,
        primaryKey: true,
        allowNull: false 
    },
    texto: {
        type: DataTypes.STRING,
        allowNull: false
    },
    coordenadaId: {
        type: DataTypes.INTEGER,
        allowNull: false,
        references: {
            model: 'coordenadas', // Nome da tabela
            key: 'id' // Chave primária da tabela coordenadas
        }
    }
}, {
    timestamps: true,
    tableName: 'comentarios' // Nome da tabela no banco de dados
});

module.exports = Comentario;