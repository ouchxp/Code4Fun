import React from 'react';
import ReactDOM from 'react-dom';

class App extends React.Component {
  constructor() {
    super();
    this.state = {
      txt: 'this is the state text'
    };
    this.update = this.update.bind(this)
  }

  update(e) {
    this.setState({ txt: e.target.value })
  }

  render() {
    return (
      <div>
        <Widget txt={this.state.txt} update={this.update} />
      </div>
    );
  }
}

const Widget = (props) => {
  return (
    <div>
      <h1>Hello World {props.txt}</h1>
      <input type="text" onChange={props.update}/>
    </div>
  )
};

Widget.propTypes = {
  txt: React.PropTypes.string.isRequired,
  update: React.PropTypes.func.isRequired
};

// Widget.defaultProps = { // use default props could pass the isRequired check
//   txt: 'default prop'
// };

// Short hand for state less function component
// const App = () => <h1>Hello World</h1>;

ReactDOM.render(<App/>, document.getElementById('app'));

export default App
