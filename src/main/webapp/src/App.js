import React, {Component} from 'react';
import {NavigationBar} from "./components/navbar/NavigationBar";
import {Layout} from "./components/navbar/Layout";
import Event from "./components/event/Event";

import './App.css';


import {
    BrowserRouter as Router,
    Switch, Route
} from "react-router-dom";
import UserEvent from "./components/event/UserEvent";
import Login from "./components/login/Login";


class App extends Component {


    render() {
    return (

            <React.Fragment>
                <NavigationBar></NavigationBar>
                <Layout>
                    <Router>
                        <Switch>
                            <Route exact path="/login" component={Login} />
                            <Route exact path="/event" component={Event} />
                            <Route exact path="/applyEvent" component={UserEvent} />

                        </Switch>
                    </Router>
                </Layout>
            </React.Fragment>








    );

    }




}

export default App;
