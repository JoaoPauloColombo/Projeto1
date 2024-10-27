const { DataTypes } = require("sequelize");
const sequelize = require("../config/config");

const Coordenadas = sequelize.define('ecoponto', {
    latitude: {
        type: DataTypes.DOUBLE,
        allowNull: false
    },
    longitude: {
        type: DataTypes.DOUBLE,
        allowNull: false
    },
    nome: {
        type: DataTypes.STRING,
        allowNull: false
    }
});

module.exports = Coordenadas;