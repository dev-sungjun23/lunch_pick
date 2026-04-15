import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { recommend } from '../api.js';
import RecommendForm from '../components/RecommendForm.jsx';
import CandidateList from '../components/CandidateList.jsx';
import useTeamMembers from '../hooks/useTeamMembers.js';

export default function LunchVotePage() {
  const { teamId: teamIdParam } = useParams();
  const teamId = Number(teamIdParam);

  const [location, setLocation] = useState('회사');
  const [maxDistance, setMaxDistance] = useState('10');
  const [budget, setBudget] = useState('10000');
  const [candidates, setCandidates] = useState([]);
  const { members } = useTeamMembers(teamId);
  const [error, setError] = useState('');
  const [loadingRec, setLoadingRec] = useState(false);
  
  const [userCoords, setUserCoords] = useState(null);
  const [locationError, setLocationError] = useState('');

  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          setUserCoords({
            latitude: position.coords.latitude,
            longitude: position.coords.longitude
          });
          console.log('Geolocation coords:', position.coords.latitude, position.coords.longitude);
          setLocationError('');
        },
        (error) => {
          console.warn('Geolocation failed:', error);
          setLocationError('Geolocation failed. Please enable location permissions.');
          setUserCoords(null);
        }
      );
    } else {
      setLocationError('Browser does not support geolocation.');
      setUserCoords(null);
    }
  }, []);

  async function handleRecommend(e) {
    e.preventDefault();
    setError('');
    
    if (!userCoords) {
      setError('Please enable location permissions first.');
      return;
    }
    
    setLoadingRec(true);
    try {
      const body = {
        teamId,
        location: location.trim(),
        maxDistance: Number(maxDistance),
        budget: Number(budget),
        latitude: userCoords.latitude,
        longitude: userCoords.longitude,
      };
      console.log('Recommend request body:', body);
      if (Number.isNaN(body.maxDistance) || Number.isNaN(body.budget)) {
        throw new Error('Invalid maxDistance or budget');
      }
      const list = await recommend(body);
      setCandidates(Array.isArray(list) ? list : []);
    } catch (err) {
      setCandidates([]);
      setError(err.message || 'Recommendation failed');
    } finally {
      setLoadingRec(false);
    }
  }

  if (Number.isNaN(teamId)) {
    return <p>잘못된 teamId</p>;
  }

  return (
    <div className="page-shell" style={{ padding: 16, maxWidth: 560, margin: '0 auto' }}>
      <h1>추천 · 투표</h1>
      <p className="info-text" style={{ marginBottom: 12 }}>
        팀원을 선택하고 원하는 후보에 투표하면, 결과 화면에서 최종 식당이 정해집니다.
      </p>
      <p className="link-row">
        <Link to={`/team/${teamId}/setup`}>← 팀원 설정</Link>
        {' · '}
        <Link to={`/team/${teamId}/result`}>결과 보기 →</Link>
      </p>

      {locationError && (
        <div style={{ 
          padding: 12, 
          backgroundColor: '#fff3cd', 
          border: '1px solid #ffeaa7', 
          borderRadius: 6,
          marginBottom: 16,
          color: '#856404'
        }}>
          {locationError}
        </div>
      )}

      <RecommendForm
        location={location}
        onLocationChange={setLocation}
        maxDistance={maxDistance}
        onMaxDistanceChange={setMaxDistance}
        budget={budget}
        onBudgetChange={setBudget}
        onSubmit={handleRecommend}
        loading={loadingRec}
        disabled={!userCoords}
      />

      <CandidateList candidates={candidates}>
        {() => null}
      </CandidateList>

      {candidates.length > 0 && (
        <p style={{ marginTop: 16, color: '#666', fontSize: 14 }}>
          추천 기능 검증 단계라 투표는 비활성화
        </p>
      )}

      {error && <p className="error-text">{error}</p>}
      <div className="next-step-wrap">
        <Link className="next-step-btn" to={`/team/${teamId}/result`}>
          결과 보기 →
        </Link>
      </div>
    </div>
  );
}
