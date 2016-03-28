import React from 'react';
import ReactDOM from 'react-dom';

class App extends React.Component {
  constructor() {
    super();
    this.state = {
      red: 0,
      green: 0,
      blue: 0
    };
    this.update = this.update.bind(this)
  }

  update(e) {
    this.setState({
      red: ReactDOM.findDOMNode(this.refs.red.refs.inp).value,
      green: ReactDOM.findDOMNode(this.refs.green.refs.inp).value,
      blue: ReactDOM.findDOMNode(this.refs.blue.refs.inp).value
    })
  }

  render() {
    return (
      <div>
        <Slider ref="red" update={this.update}/> {this.state.red}
        <Slider ref="green" update={this.update}/> {this.state.green}
        <Slider ref="blue" update={this.update}/> {this.state.blue}
        <Button>I <Heart/> React</Button>
      </div>
    );
  }
}

class Slider extends React.Component {
  render () {
    return (
      <div>
        <input ref="inp" type="range" min="0" max="255" onChange={this.props.update} />
      </div>
    );
  }
}

/** Use this.props.children to reference the innerHTML of component */
class Button extends React.Component {
  render () {
    return <button>{this.props.children}</button>
  }
}

const Heart = () => <span className="glyphicon glyphicon-heart"/>;

// const Widget = (props) => {
//   return (
//     <div>
//       <h1>Hello World {props.txt}</h1>
//       <input type="text" onChange={props.update}/>
//     </div>
//   )
// };
//
// Widget.propTypes = {
//   txt: React.PropTypes.string.isRequired,
//   update: React.PropTypes.func.isRequired
// };

// Widget.defaultProps = { // use default props could pass the isRequired check
//   txt: 'default prop'
// };

// Short hand for state less function component
// const App = () => <h1>Hello World</h1>;

ReactDOM.render(<App/>, document.getElementById('app'));

export default App
