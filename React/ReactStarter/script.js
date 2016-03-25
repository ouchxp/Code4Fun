const Button = React.createClass({
  propTypes: {
    inc: React.PropTypes.number.isRequired,
    onClick: React.PropTypes.func.isRequired
  },
  clickHandler: function() {
    this.props.onClick(this.props.inc)
  },
  render: function () {
    return (
      <button onClick={this.clickHandler}>
        {this.props.inc >= 0 ? "+" : ""}{this.props.inc}
      </button>
    )
  }
});

const Result = React.createClass({
  propTypes: {
    counter: React.PropTypes.number.isRequired
  },
  render: function () {
    return (
      <div>{this.props.counter}</div>
    )
  }
});

const Main = React.createClass({
  getInitialState: function () {
    return {counter: 0};
  },
  increment: function (x) {
    this.setState({counter: this.state.counter + x})
  },
  render: function () {
    return (
      <div>
        <Button onClick={this.increment} inc={+1}/>
        <Button onClick={this.increment} inc={-1}/>
        <Button onClick={this.increment} inc={+100}/>
        <Button onClick={this.increment} inc={-100}/>
        <Result counter={this.state.counter}/>
      </div>
    )
  }
});

ReactDOM.render(<Main/>, document.getElementById('root'));
