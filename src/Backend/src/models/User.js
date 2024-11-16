// src/models/User.js
const { DataTypes } = require("sequelize");
const sequelize = require("../config/config");

const User = sequelize.define('User ', { 
    id: {
        type: DataTypes.INTEGER,
        autoIncrement: true,
        primaryKey: true,
        allowNull: false 
    },
    nome: {
        type: DataTypes.STRING,
        allowNull: false
    },
    email: {
        type: DataTypes.STRING,
        allowNull: false,
        unique: true, 
        validate: {
            isEmail: true 
        }
    },
    senha: {
        type: DataTypes.STRING,
        allowNull: false
    }
}, {
    timestamps: true, 
    tableName: 'users'
});

// Definindo associações
User .associate = (models) => {
    User.hasMany(models.Comentario, { foreignKey: 'userId' });
};

module.exports = User;