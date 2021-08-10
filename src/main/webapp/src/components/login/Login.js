import React, { Component } from 'react';
import axios from 'axios';
import Button from "@material-ui/core/Button";
import {makeStyles} from "@material-ui/core/styles";
import withStyles from "@material-ui/core/styles/withStyles";
import LockOutlinedIcon from "@material-ui/icons/LockOutlined";
import Avatar from "@material-ui/core/Avatar";
import Typography from "@material-ui/core/Typography";
import {TextField} from "@material-ui/core";


const useStyles = makeStyles((theme) => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.primary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(1),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
}));

class Login extends Component {
    constructor(props) {
        super(props);

        this.state = {
            username: '',
            password: ''
        };

    }

    change(e) {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    submit(e) {
        e.preventDefault();
        axios.post('/login', {
            username: this.state.username,
            password: this.state.password
        }).then(res => {
            const {token} = res.data;
            localStorage.setItem('cool-jwt', token);
            this.props.history.push('/event');
        });
    }
    render() {
        const { classes } = this.props;

        return (
            <div className={classes.paper}>
                <Avatar className={classes.avatar}>
                    <LockOutlinedIcon />
                </Avatar>
                <Typography component="h1" variant="h5">
                    LOGIN
                </Typography>
                <form className={classes.form} onSubmit={e => this.submit(e)}>
                   <TextField variant="outlined"
                              margin="normal"
                              required
                              fullWidth
                              label="Username"
                              type="text"
                              name="username"
                              onChange={e => this.change(e)}
                              value={this.state.username} />
                   <TextField variant="outlined"
                              margin="normal"
                              required
                              fullWidth
                              label="Password"
                              type="password"
                              name="password"
                              onChange={e => this.change(e)}
                              value={this.state.password} />
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                    >LOGIN
                    </Button>
                </form>
            </div>
        );
    }
}

export default withStyles(useStyles) (Login);