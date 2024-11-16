const { DataTypes } = require("sequelize");
const sequelize = require("../config/config");

const Coordenadas = sequelize.define('coordenadas', {
    id: {
        type: DataTypes.INTEGER,
        autoIncrement: true,
        primaryKey: true
    },
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