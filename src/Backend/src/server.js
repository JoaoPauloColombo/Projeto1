require('dotenv').config();
const express = require("express");
const router = require("./router/router");
const sequelize = require("./config/config");

const Comentario = require("./models/Comentario");
const User = require("./models/User");
const routerUpload = require('./router/routerUpload');
const Coordenadas = require("./models/Coordenadas");
const Associations = require("./models/associations"); // Corrigido para 'associations'

// Importando o CORS
var cors = require('cors');

const app = express();

app.use(cors());

app.use((req, res, next) => {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Methods", 'GET,PUT,POST,DELETE');
  next();
});

// Modelo da API JSON
app.use(express.json());
app.use(express.urlencoded({ extended: true })); // Adicione essa linha

app.use("/api", router);
app.use('/api/image', routerUpload);

app.get("/healthcheck", (req, res) => {
  // 200 significa que está ok o servidor
  return res.status(200).json({
    msg: "Estamos vivos",
    alive: true,
  });
});

// Autenticação e sincronização do banco de dados
sequelize
  .authenticate()
  .then(async () => {
    console.log("Conexão estabelecida com sucesso");
    await sequelize.sync(); // Sincroniza o código com a tabela
  })
  .then(() => {
    app.listen(process.env.PORT || 8080, () => {
      console.log(`Rodando na porta ${process.env.PORT || 8080}`);
    });
  })
  .catch((error) => { 
    console.error("Erro ao se conectar com o banco", error);
  });