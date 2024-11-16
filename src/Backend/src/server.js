require('dotenv').config();
const express = require("express");
const router = require("./router/router");
const sequelize = require("./config/config");
const cors = require('cors');

const app = express();

app.use(cors());

app.use(express.json());
app.use(express.urlencoded({ extended: true }));


app.use("/api", router);
app.use('/api/image', require('./router/routerUpload'));


app.get("/healthcheck", (req, res) => {
  return res.status(200).json({
    msg: "Estamos vivos",
    alive: true,
  });
});

app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).json({ error: 'Algo deu errado!' });
});

sequelize
  .authenticate()
  .then(async () => {
    console.log("Conexão estabelecida com sucesso");
    await sequelize.sync();
  })
  .then(() => {
    const port = process.env.PORT || 8080;
    app.listen(port, () => {
      console.log(`Rodando na porta ${port}`);
    });
  })
  .catch((error) => { 
    console.error("Erro ao se conectar com o banco", error);
  });