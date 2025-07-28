import React from 'react';
import { Link } from 'react-router-dom';
import logo from '../assets/logo.png';

const Navbar: React.FC = () => {
  return (
    <nav style={{ padding: '1rem', background: '#222', color: '#fff' }}>
      <Link to="/home" style={{ color: 'white', textDecoration: 'none' }}>
        <img src={logo} className='size-10'></img>
      </Link>
    </nav>
  );
};

export default Navbar;
