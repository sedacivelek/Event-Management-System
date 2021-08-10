import React from 'react';
import {Nav,Navbar} from 'react-bootstrap';
import styled from 'styled-components';

const Styles = styled.div`
    .navbar{
        background-color: #b0cac7;
       
    }

    .navbar-brand, .navbar-nav, .nav-link .navbar-toggle{
        color: #222831;
        &:hover{
            color: white;
        }
    }
    
`;
export const NavigationBar = () =>(
    <Styles>
        <Navbar expand="lg">
            <Navbar.Brand href="/">EVENT HERO</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav"></Navbar.Toggle>
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="ml-auto">
                    <Nav.Item><Nav.Link href="/login">Login</Nav.Link></Nav.Item>
                </Nav>
                <Nav className="ml-auto">
                    <Nav.Item><Nav.Link href="/event">Events</Nav.Link></Nav.Item>
                </Nav>
                <Nav>
                    <Nav.Item><Nav.Link href="/applyEvent">Apply Event</Nav.Link></Nav.Item>
                </Nav>

            </Navbar.Collapse>
        </Navbar>
    </Styles>
)