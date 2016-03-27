const StartsFrame = React.createClass({
  propTypes: {
    numberOfStars: React.PropTypes.number.isRequired
  },
  render: function () {
    const stars = new Array(this.props.numberOfStars).fill(0)
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
  propTypes: {
    selectedNumbers: React.PropTypes.arrayOf(React.PropTypes.number).isRequired,
    correct: React.PropTypes.bool,
    checkAnswer: React.PropTypes.func.isRequired
  },
  render: function () {

    let button = undefined;
    switch (this.props.correct) {
      case true:
        button = <button className="btn btn-success btn-lg">
          <span className="glyphicon glyphicon-ok"/>
        </button>;
        break;
      case false:
        button = <button className="btn btn-danger btn-lg">
          <span className="glyphicon glyphicon-remove"/>
        </button>;
        break;
      default:
        const disabled = this.props.selectedNumbers.length === 0;
        button = <button onClick={this.props.checkAnswer} className="btn btn-primary btn-lg" disabled={disabled}>=</button>
    }

    return (
      <div id="button-frame">
        {button}
      </div>
    );
  }
});

const AnswerFrame = React.createClass({
  propTypes: {
    selectedNumbers: React.PropTypes.arrayOf(React.PropTypes.number).isRequired,
    unselectNumber: React.PropTypes.func.isRequired
  },
  render: function () {

    const selectedNumbers = this.props.selectedNumbers.map(i => <span key={i} onClick={this.props.unselectNumber.bind(null, i)}>{i}</span>);
    return (
      <div id="answer-frame">
        <div className="well">
          {selectedNumbers}
        </div>
      </div>
    );
  }
});

const NumbersFrame = React.createClass({
  propTypes: {
    selectedNumbers: React.PropTypes.arrayOf(React.PropTypes.number).isRequired,
    selectNumber: React.PropTypes.func.isRequired
  },
  render: function () {
    const selectedNumbers = this.props.selectedNumbers;
    const numbers = new Array(9).fill(0)
      .map((elem, idx) => idx + 1)
      .map(idx => <div key={idx} onClick={this.props.selectNumber.bind(null, idx)}
                       className={"number" + (selectedNumbers.indexOf(idx) >=0 ? " selected-true" : "")}>{idx}</div>);
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
    return {
      numberOfStars: Math.floor(Math.random() * 9) + 1,
      selectedNumbers: [],
      correct: null
    };
  },
  selectNumber: function (selectedNumber) {
    if (this.state.selectedNumbers.indexOf(selectedNumber) >= 0)
      return;
    this.setState({
      selectedNumbers: this.state.selectedNumbers.concat(selectedNumber),
      correct: null
    })
  },
  unselectNumber: function (unselectedNumber) {
    const selectedNumbers = this.state.selectedNumbers;
    const idx = selectedNumbers.indexOf(unselectedNumber);
    selectedNumbers.splice(idx, 1);
    this.setState({
      selectedNumbers: selectedNumbers,
      correct: null
    })
  },
  checkAnswer: function () {
    const sum = this.state.selectedNumbers.reduce((x, y) => x + y, 0);
    const correct = this.state.numberOfStars === sum;
    this.setState({correct: correct});
  },
  render: function () {
    const selectedNumbers = this.state.selectedNumbers;
    const numberOfStars = this.state.numberOfStars;
    return (
      <div id="game">
        <h2>Play Nine</h2>
        <hr/>
        <div className="clearfix">
          <StartsFrame numberOfStars={numberOfStars}/>
          <ButtonFrame selectedNumbers={selectedNumbers} correct={this.state.correct} checkAnswer={this.checkAnswer}/>
          <AnswerFrame selectedNumbers={selectedNumbers} unselectNumber={this.unselectNumber}/>
        </div>
        <NumbersFrame selectedNumbers={selectedNumbers} selectNumber={this.selectNumber}/>
      </div>
    );
  }
});


ReactDOM.render(<Game/>, document.getElementById("container"));