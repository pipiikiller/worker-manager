const webpack = require("webpack");

module.exports = {
  devServer: {
    port: 3000,
    // allow calling api from external server during dev
    proxy: {
      "^/api": {
        target: "http://localhost:8080/",
        ws: true,
        // 'false' if we want to disable CORS and 'true' otherwise
        changeOrigin: false
      }
    }
  },
  lintOnSave: false,
  configureWebpack: {
    // Set up all the aliases we use in our app.
    resolve: {
      alias: {
        "chart.js": "chart.js/dist/Chart.js"
      }
    },
    plugins: [
      new webpack.optimize.LimitChunkCountPlugin({
        maxChunks: 6
      })
    ]
  },
  pwa: {
    name: "Vue Black Dashboard",
    themeColor: "#344675",
    msTileColor: "#344675",
    appleMobileWebAppCapable: "yes",
    appleMobileWebAppStatusBarStyle: "#344675"
  },
  chainWebpack: config => {
    config.plugins.delete("pwa");
    config.plugins.delete("workbox");
  },
  pluginOptions: {
    i18n: {
      locale: "en",
      fallbackLocale: "en",
      localeDir: "locales",
      enableInSFC: false
    }
  },
  css: {
    // Enable CSS source maps.
    sourceMap: process.env.NODE_ENV !== "production"
  },
  outputDir: "target/dist",
  assetsDir: "static",
  indexPath: "../index.html"
};
