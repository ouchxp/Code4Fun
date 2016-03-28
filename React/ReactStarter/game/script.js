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
    checkAnswer: React.PropTypes.func.isRequired,
    acceptAnswer: React.PropTypes.func.isRequired,
    redraw: React.PropTypes.func.isRequired,
    redraws: React.PropTypes.number.isRequired
  },
  render: function () {

    let button = undefined;
    switch (this.props.correct) {
      case true:
        button = <button className="btn btn-success btn-lg">
          <span onClick={this.props.acceptAnswer} className="glyphicon glyphicon-ok"/>
        </button>;
        break;
      case false:
        button = <button className="btn btn-danger btn-lg">
          <span className="glyphicon glyphicon-remove"/>
        </button>;
        break;
      default:
        const disabled = this.props.selectedNumbers.length === 0;
        button =
          <button onClick={this.props.checkAnswer} className="btn btn-primary btn-lg" disabled={disabled}>=</button>
    }

    return (
      <div id="button-frame">
        {button}
        <br/>
        <br/>
        <button className="btn btn-warning btn-xs" onClick={this.props.redraw} disabled={this.props.redraws === 0}>
          <span className="glyphicon glyphicon-refresh"/>
          &nbsp;
          {this.props.redraws}
        </button>
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

    const selectedNumbers = this.props.selectedNumbers.map(i => <span key={i}
                                                                      onClick={this.props.unselectNumber.bind(null, i)}>{i}</span>);
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
    usedNumbers: React.PropTypes.arrayOf(React.PropTypes.number).isRequired,
    selectNumber: React.PropTypes.func.isRequired
  },
  render: function () {
    const selectedNumbers = this.props.selectedNumbers;
    const usedNumbers = this.props.usedNumbers;
    const numbers = new Array(9).fill(0)
      .map((elem, idx) => idx + 1)
      .map(idx => {
        const className = "number"
          + (selectedNumbers.indexOf(idx) >= 0 ? " selected-true" : "")
          + (usedNumbers.indexOf(idx) >= 0 ? " used-true" : "");

        return <div key={idx} onClick={this.props.selectNumber.bind(null, idx)}
                    className={className}>{idx}</div>;
      });
    return (
      <div id="numbers-frame">
        <div className="well">
          {numbers}
        </div>
      </div>
    );
  }
});

const DoneFrame = React.createClass({
  propTypes: {
    doneStatus: React.PropTypes.string.isRequired,
    resetGame: React.PropTypes.func.isRequired
  },
  render: function () {
    return (
      <div className="well text-center">
        <h2>{this.props.doneStatus}</h2>
        <button className="btn btn-default" onClick={this.props.resetGame}>Play Again</button>
      </div>
    );
  }
});

const Game = React.createClass({
  getInitialState: function () {
    return {
      numberOfStars: this.randomNumber(),
      selectedNumbers: [],
      usedNumbers: [],
      correct: null,
      redraws: 5,
      doneStatus: null
    };
  },
  randomNumber: () => Math.floor(Math.random() * 9) + 1,
  resetGame: function () {
    this.replaceState(this.getInitialState())
  },
  selectNumber: function (selectedNumber) {
    if (this.state.selectedNumbers.indexOf(selectedNumber) >= 0 || this.state.usedNumbers.indexOf(selectedNumber) >= 0)
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
  acceptAnswer: function () {
    const usedNumbers = this.state.usedNumbers.concat(this.state.selectedNumbers);
    this.setState({
      selectedNumbers: [],
      usedNumbers: usedNumbers,
      correct: null,
      numberOfStars: this.randomNumber()
    }, this.updateDoneStatus); // callback when set state is done
  },
  redraw: function () {
    if (this.state.redraws > 0) {
      this.setState({
        selectedNumbers: [],
        numberOfStars: this.randomNumber(),
        correct: null,
        redraws: this.state.redraws - 1
      }, this.updateDoneStatus);
    }
  },
  possibleCombinationSum: function (arr, n) {
    if (arr.indexOf(n) >= 0) {
      return true;
    }
    if (arr[0] > n) {
      return false;
    }
    if (arr[arr.length - 1] > n) {
      arr.pop();
      return this.possibleCombinationSum(arr, n);
    }
    var listSize = arr.length, combinationsCount = (1 << listSize)
    for (var i = 1; i < combinationsCount; i++) {
      var combinationSum = 0;
      for (var j = 0; j < listSize; j++) {
        if (i & (1 << j)) {
          combinationSum += arr[j];
        }
      }
      if (n === combinationSum) {
        return true;
      }
    }
    return false;
  },
  possibleSolution: function () {
    const numberOfStars = this.state.numberOfStars;
    const possibleNumbers = [];
    const usedNumbers = this.state.usedNumbers;

    for (let i = 1; i <= 9; i++) {
      if (usedNumbers.indexOf(i) < 0) {
        possibleNumbers.push(i)
      }
    }

    return this.possibleCombinationSum(possibleNumbers, numberOfStars);
  },
  updateDoneStatus: function () {
    if (this.state.usedNumbers.length === 9) {
      this.setState({doneStatus: 'Done. Nice!'});
      return;
    }

    if (this.state.redraws === 0 && !this.possibleSolution()) {
      this.setState({doneStatus: 'Game Over!'});
    }
  },
  render: function () {
    const selectedNumbers = this.state.selectedNumbers;
    const usedNumbers = this.state.usedNumbers;
    const numberOfStars = this.state.numberOfStars;
    const bottomFrame = this.state.doneStatus ? <DoneFrame doneStatus={this.state.doneStatus} resetGame={this.resetGame} /> :
      <NumbersFrame selectedNumbers={selectedNumbers} selectNumber={this.selectNumber}
                    usedNumbers={usedNumbers}/>;

    return (
      <div id="game">
        <h2>Play Nine</h2>
        <hr/>
        <div className="clearfix">
          <StartsFrame numberOfStars={numberOfStars}/>
          <ButtonFrame selectedNumbers={selectedNumbers} correct={this.state.correct}
                       checkAnswer={this.checkAnswer} acceptAnswer={this.acceptAnswer}
                       redraw={this.redraw} redraws={this.state.redraws}/>
          <AnswerFrame selectedNumbers={selectedNumbers} unselectNumber={this.unselectNumber}/>
        </div>
        {bottomFrame}
      </div>
    );
  }
});


ReactDOM.render(<Game/>, document.getElementById("container"));