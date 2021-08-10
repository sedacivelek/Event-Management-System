import React, {Component} from 'react';
import axios from "axios";
import LoginForm from "./LoginForm";

export default class LoginPage extends Component{

    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: ''
        };

    }
    login = (inputData) => {
        this.state.username=inputData.username;
        this.state.password=inputData.password;
        axios.post("/login", {username: this.state.username,
            password: this.state.password})
            .then(res => {
                localStorage.setItem('cool-jwt', res.data);
                this.props.history.push('/event');
            });
    }

    render() {
        return (
            <div>
                <LoginForm onSubmit={this.login}/>
            </div>
        );
    }
}