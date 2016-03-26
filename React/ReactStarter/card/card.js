// ES6 flavor
// see https://facebook.github.io/react/blog/2015/01/27/react-v0.13.0-beta-1.html
class HelloWorld extends React.Component {
  render() {
    return <p>Hello, world!</p>;
  }
}
//HelloWorld.propTypes = {login: React.PropTypes.string.isRequired};

const Card = React.createClass({
  propTypes: {
    login: React.PropTypes.string.isRequired
  },
  getInitialState: function () {
    return {};
  },
  componentDidMount: function () {
    const self = this;
    $.get("https://api.github.com/users/" + this.props.login, function (data) {
      self.setState(data)
    });

  },
  render: function () {
    return (
      <div>
        <img src={this.state.avatar_url} width="80"/>
        <h3>{this.state.name}</h3>
        <hr/>
      </div>
    );
  }
});

const Form = React.createClass({
  propTypes: {
    addCard: React.PropTypes.func.isRequired
  },
  handleSubmit: function (e) {
    e.preventDefault();
    const loginInput = ReactDOM.findDOMNode(this.refs.login);
    this.props.addCard(loginInput.value);
    loginInput.value = '';
  },
  render: function () {
    return (
      <form onSubmit={this.handleSubmit}>
        <input type="text" placeholder="github login" ref="login"/>
        <button>Add</button>
      </form>
    );
  }
});

const Main = React.createClass({
  getInitialState: function () {
    return {logins: ["ouchxp", "echojc"]};
  },
  addCard: function (login) {
    this.setState({logins: [login].concat(this.state.logins)});
  },
  render: function () {
    // iterable components must have a key prop
    var cards = this.state.logins.map(x => <Card key={x} login={x}/>);
    return (
      <div>
        <HelloWorld />
        <Form addCard={this.addCard} />
        {cards}
      </div>
    )
  }
});

ReactDOM.render(<Main/>, document.getElementById('root'));
