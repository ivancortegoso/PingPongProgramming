import React from 'react'
import '../styles/FormStyle.css'
import {useState} from 'react'
import { useNavigate } from 'react-router';

export const LogInWeb = () => {
    const [errorMessage, setErrorMessage] = useState("");
    const navigate = useNavigate();

    const delay = ms => new Promise(res => setTimeout(res, ms));

    const login = async(data) => {
        try {
            
            const response = await fetch("http://localhost:8080/api/public/login", {
                method: 'post',
                headers:{'Content-Type' : 'application/json'},
                body: JSON.stringify(data),

            });

            if(response.ok)
            {
                const dd = await response.json();
                localStorage.setItem("token", "Bearer " + dd["token"]);
                (function one() {
                    console.log("baila");
                    if(localStorage.getItem("token") === ("Bearer " + dd["token"])) {
                        navigate("/bankaccounts", {replace:true});
                    } else {
                        setTimeout(one, 30);
                    }
                })();
                //window.location.reload();
            }
            else
            {
                setErrorMessage("System error");
            }
        } catch(e) {
            setErrorMessage("Network error");
        }

        
    }

    const submit = (e) => {
        e.preventDefault();
        const data = {
            username: e.target.username.value,
            password: e.target.password.value
        };
        login(data);

        console.log(data);
    }

    return (
        <div className="LogInWeb BaseFormBox ShadowBox">
            <h3>Log In</h3>
            <div className={"HSeparator"}></div>
            <form className={"BaseForm"} onSubmit={submit}>
                <div className="FormGroup">
                    <label>Username</label><input type="text" name="username" placeholder="Username"/>
                </div>
                <div className="FormGroup">
                    <label>Password</label><input type="password" name="password" placeholder="Password"/>
                </div>
                {errorMessage.length > 0 && 
                    <label id="error-message" style={{color:'red'}}>{errorMessage}</label>
                }
                <input className="StandardFormSubmit" type="submit" value={"Log In"}/>
            </form>
        </div>
    );
}