const path = require('path');

module.exports = {
  entry: './src/main/webapp/javascript/index.js',
  output: {
    filename: 'bundle.js',
    path: path.resolve(__dirname, 'src/main/resources/static/dist')
  },
  mode: 'development'
};