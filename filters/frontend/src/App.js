import React, { Component } from 'react';
import './App.css';
import { BrowserRouter as Router, Route} from 'react-router-dom';
import FilterList from './FilterList';

class App extends Component {
  render() {
    return (
        <Router>
            <Route path={["/filters", "/"]} exact={false} component={FilterList}/>
        </Router>
    )
  }
}

export default App;