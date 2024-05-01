const { defineConfig } = require('@vue/cli-service')
const path = require("path")

const host = "localhost";
const port = "8080"; // 백엔드 서버 포트 번호

module.exports = defineConfig({
  transpileDependencies: true,

  outputDir: path.resolve(__dirname, "../back-end/src/main/resources/static"),

  devServer: {
    hot: true,
    proxy: {

      '/api/': {
        target: `http://${host}:${port}`,
        changeOrigin: true,
      },
      // WebSocket proxy 설정
      '/ws/': {
        target: `ws://${host}:${port}`,
        changeOrigin: true,
        ws: true,
      }
    }
  }
})
