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
  render: function () {
    return (
      <div id="answer-frame">
        <div className="well">...</div>
      </div>
    );
  }
});

const NumbersFrame = React.createClass({
  render: function () {

    const numbers = new Array(9).fill(0)
      .map((elem, idx) => <div key={idx+1} className="number">{idx + 1}</div>);
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
  render: function () {
    return (
      <div id="game">
        <h2>Play Nine</h2>
        <hr/>
        <div className="clearfix">
          <StartsFrame/>
          <ButtonFrame/>
          <AnswerFrame/>
        </div>
        <NumbersFrame/>
      </div>
    );
  }
});


ReactDOM.render(<Game/>, document.getElementById("container"));