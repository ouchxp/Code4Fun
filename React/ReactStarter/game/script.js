const StartsFrame = React.createClass({
  render: function () {
    const numberOfStars = Math.floor(Math.random() * 9) + 1;
    const stars = new Array(numberOfStars).fill(0)
      .map((elem, idx) => <span key={idx} className="glyphicon glyphicon-star"/>);
    return (
      <div id="stars-frame">
        <div className="well">
          {stars}
        </div>
      </div>
    );
  }
});

const ButtonFrame = React.createClass({
  render: function () {
    return (
      <div id="button-frame">
        <button className="btn btn-primary btn-lg">=</button>
      </div>
    );
  }
});

const AnswerFrame = React.createClass({
  propTypes: {
    selectedNumbers: React.PropTypes.arrayOf(React.PropTypes.number).isRequired
  },
  render: function () {
    return (
      <div id="answer-frame">
        <div className="well">
          {this.props.selectedNumbers}
        </div>
      </div>
    );
  }
});

const NumbersFrame = React.createClass({
  propTypes: {
    selectedNumbers: React.PropTypes.arrayOf(React.PropTypes.number).isRequired
  },
  render: function () {
    const numbers = new Array(9).fill(0)
      .map((elem, idx) => idx + 1)
      .map(idx => <div key={idx} className={"number" + (this.props.selectedNumbers.indexOf(idx) >=0 ? " selected-true" : "")}>{idx}</div>);
    return (
      <div id="numbers-frame">
        <div className="well">
          {numbers}
        </div>
      </div>
    );
  }
});

const Game = React.createClass({
  getInitialState: function () {
    return {selectedNumbers: [3, 6]};
  },
  render: function () {
    return (
      <div id="game">
        <h2>Play Nine</h2>
        <hr/>
        <div className="clearfix">
          <StartsFrame/>
          <ButtonFrame/>
          <AnswerFrame selectedNumbers={this.state.selectedNumbers}/>
        </div>
        <NumbersFrame selectedNumbers={this.state.selectedNumbers}/>
      </div>
    );
  }
});


ReactDOM.render(<Game/>, document.getElementById("container"));