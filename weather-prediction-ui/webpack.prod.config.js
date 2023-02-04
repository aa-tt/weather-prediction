const path = require('path');
const HtmlWebPackPlugin = require("html-webpack-plugin");
const ModuleFederationPlugin = require("webpack/lib/container/ModuleFederationPlugin");
const CopyPlugin = require("copy-webpack-plugin");

const deps = require("./package.json").dependencies;
module.exports = {
  mode: 'development',
  devtool: 'source-map',
  entry: {
    main: [
      path.join(__dirname, "/src/index.ts")
    ]
  },
  output: {
    path: path.join(__dirname, 'dist'),
    filename: 'main.js',
  },

  resolve: {
    extensions: [".tsx", ".ts", ".jsx", ".js", ".json"],
    modules: [path.resolve(__dirname, 'src'), 'node_modules'],
  },

  devServer: {
    port: 5001,
    historyApiFallback: true,
    allowedHosts: "all",
  },

  module: {
    rules: [
      {
        test: /\.m?js/,
        type: "javascript/auto",
        resolve: {
          fullySpecified: false,
        },
      },
      {
        test: /\.(css|s[ac]ss)$/i,
        use: ["style-loader", "css-loader", "postcss-loader"],
      },
      {
        test: /\.(ts|tsx|js|jsx)$/,
        exclude: /node_modules/,
        use: {
          loader: "babel-loader",
        },
      },
      {
        test: /\.svg$/i,
        type: "asset",
        resourceQuery: /url/,
      }
    ],
  },

  plugins: [
    new ModuleFederationPlugin({
      name: "weather_prediction_ui",
      filename: "remoteEntry.js",
      remotes: {},
      exposes: {},
      shared: {
        ...deps,
        react: {
          singleton: true,
          requiredVersion: deps.react,
        },
        "react-dom": {
          singleton: true,
          requiredVersion: deps["react-dom"],
        },
      },
    }),
    new HtmlWebPackPlugin({
      template: path.join(__dirname, 'src', 'index.html'),
    }),
    new CopyPlugin([
      {
        from: "public/images/*.svg",
        to: "public/images",
      }
    ])
  ],
};
