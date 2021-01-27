module.exports = {
  publicPath: "/LenaBookstoreTransact/",
  chainWebpack: config => {
    config.plugin("html").tap(args => {
      args[0].title = "Ink Bookshop";
      return args;
    });
  }
};
