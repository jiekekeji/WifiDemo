package com.listong.util;

import java.net.Inet4Address;
import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.util.Log;

import com.listong.util.WifiConnect.WifiCipherType;

/**
 * Class Name: WifiAdmin.java<br>
 * Function:Wifi���ӹ�������<br>
 * 
 * Modifications:<br>
 * 
 * @author ZYT DateTime 2014-5-14 ����2:24:14<br>
 * @version 1.0<br>
 * <br>
 */
public class WifiAdmin {
	// ����һ��WifiManager����
	private WifiManager mWifiManager;
	// ����һ��WifiInfo����
	private WifiInfo mWifiInfo;
	// ɨ��������������б�
	private List<ScanResult> mWifiList;
	// ���������б�
	private List<WifiConfiguration> mWifiConfigurations;
	WifiLock mWifiLock;

	public WifiAdmin(Context context) {
		// ȡ��WifiManager����
		mWifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		// ȡ��WifiInfo����
		mWifiInfo = mWifiManager.getConnectionInfo();
	}

	/**
	 * Function:�ر�wifi<br>
	 * www.javaapk.com����
	 * @author ZYT DateTime 2014-5-15 ����12:17:37<br>
	 * @return<br>
	 */
	public boolean closeWifi() {
		                if (mWifiManager.isWifiEnabled()) {
		                        return mWifiManager.setWifiEnabled(false);
		                }
		                return false;
		        }

	/**
	 * Gets the Wi-Fi enabled state.��鵱ǰwifi״̬
	 * 
	 * @return One of {@link WifiManager#WIFI_STATE_DISABLED},
	 *         {@link WifiManager#WIFI_STATE_DISABLING},
	 *         {@link WifiManager#WIFI_STATE_ENABLED},
	 *         {@link WifiManager#WIFI_STATE_ENABLING},
	 *         {@link WifiManager#WIFI_STATE_UNKNOWN}
	 * @see #isWifiEnabled()
	 */
	public int checkState() {
		return mWifiManager.getWifiState();
	}

	// ����wifiLock
	public void acquireWifiLock() {
		mWifiLock.acquire();
	}

	// ����wifiLock
	public void releaseWifiLock() {
		// �ж��Ƿ�����
		if (mWifiLock.isHeld()) {
			mWifiLock.acquire();
		}
	}

	// ����һ��wifiLock
	public void createWifiLock() {
		mWifiLock = mWifiManager.createWifiLock("test");
	}

	// �õ����úõ�����
	public List<WifiConfiguration> getConfiguration() {
		return mWifiConfigurations;
	}

	// ָ�����úõ������������
	public void connetionConfiguration(int index) {
		if (index > mWifiConfigurations.size()) {
			return;
		}
		// �������ú�ָ��ID������
		mWifiManager.enableNetwork(mWifiConfigurations.get(index).networkId,
				true);
	}

	public void startScan() {

		// openWifi();

		mWifiManager.startScan();
		// �õ�ɨ����
		mWifiList = mWifiManager.getScanResults();
		// �õ����úõ���������
		mWifiConfigurations = mWifiManager.getConfiguredNetworks();

	}

	// �õ������б�
	public List<ScanResult> getWifiList() {
		return mWifiList;
	}

	// �鿴ɨ����
	public StringBuffer lookUpScan() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mWifiList.size(); i++) {
			sb.append("Index_" + new Integer(i + 1).toString() + ":");
			// ��ScanResult��Ϣת����һ���ַ�����
			// ���аѰ�����BSSID��SSID��capabilities��frequency��level
			sb.append((mWifiList.get(i)).toString()).append("\n");
		}
		return sb;
	}

	public String getMacAddress() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
	}

	/**
	 * Return the basic service set identifier (BSSID) of the current access
	 * point. The BSSID may be {@code null} if there is no network currently
	 * connected.
	 * 
	 * @return the BSSID, in the form of a six-byte MAC address:
	 *         {@code XX:XX:XX:XX:XX:XX}
	 */
	public String getBSSID() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
	}

	public int getIpAddress() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
	}

	/**
	 * Each configured network has a unique small integer ID, used to identify
	 * the network when performing operations on the supplicant. This method
	 * returns the ID for the currently connected network.
	 * 
	 * @return the network ID, or -1 if there is no currently connected network
	 */
	public int getNetWordId() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
	}

	/**
	 * Function: �õ�wifiInfo��������Ϣ<br>
	 * 
	 * @author ZYT DateTime 2014-5-14 ����11:03:32<br>
	 * @return<br>
	 */
	public String getWifiInfo() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
	}

	// ���һ�����粢����
	public void addNetWork(WifiConfiguration configuration) {
		int wcgId = mWifiManager.addNetwork(configuration);
		mWifiManager.enableNetwork(wcgId, true);
	}

	// �Ͽ�ָ��ID������
	public void disConnectionWifi(int netId) {
		mWifiManager.disableNetwork(netId);
		mWifiManager.disconnect();
	}

	/**
	 * Function: ��wifi����<br>
	 * 
	 * @author ZYT DateTime 2014-5-14 ����11:01:11<br>
	 * @return true:�򿪳ɹ���false:��ʧ��<br>
	 */
	public boolean openWifi() {
		boolean bRet = true;
		if (!mWifiManager.isWifiEnabled()) {
			bRet = mWifiManager.setWifiEnabled(true);
		}
		return bRet;
	}

	/**
	 * Function: �ṩһ���ⲿ�ӿڣ�����Ҫ���ӵ������� <br>
	 * 
	 * @author ZYT DateTime 2014-5-13 ����11:46:54<br>
	 * @param SSID
	 *            SSID
	 * @param Password
	 * @param Type
	 * <br>
	 *            û���룺{@linkplain WifiCipherType#WIFICIPHER_NOPASS}<br>
	 *            WEP���ܣ� {@linkplain WifiCipherType#WIFICIPHER_WEP}<br>
	 *            WPA���ܣ� {@linkplain WifiCipherType#WIFICIPHER_WPA}
	 * @return true:���ӳɹ���false:����ʧ��<br>
	 */
	public boolean connect(String SSID, String Password, WifiCipherType Type) {
		if (!this.openWifi()) {
			return false;
		}
		// ����wifi������Ҫһ��ʱ��(�����ֻ��ϲ���һ����Ҫ1-3������)������Ҫ�ȵ�wifi
		// ״̬���WIFI_STATE_ENABLED��ʱ�����ִ����������
		while (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
			try {
				// Ϊ�˱������һֱwhileѭ��������˯��100�����ڼ�⡭��
				Thread.currentThread();
				Thread.sleep(100);
			} catch (InterruptedException ie) {
			}
		}

		System.out.println("WifiAdmin#connect==���ӽ���");

		WifiConfiguration wifiConfig = createWifiInfo(SSID, Password, Type);
		//
		if (wifiConfig == null) {
			return false;
		}

		WifiConfiguration tempConfig = this.isExsits(SSID);

		int tempId = wifiConfig.networkId;
		if (tempConfig != null) {
			tempId = tempConfig.networkId;
			mWifiManager.removeNetwork(tempConfig.networkId);
		}

		int netID = mWifiManager.addNetwork(wifiConfig);

		// �Ͽ�����
		mWifiManager.disconnect();
		// ��������
		// netID = wifiConfig.networkId;
		// ����Ϊtrue,ʹ���������ӶϿ�
		boolean bRet = mWifiManager.enableNetwork(netID, true);
		mWifiManager.reconnect();
		return bRet;
	}

	// �鿴��ǰ�Ƿ�Ҳ���ù��������
	private WifiConfiguration isExsits(String SSID) {
		List<WifiConfiguration> existingConfigs = mWifiManager
				.getConfiguredNetworks();
		for (WifiConfiguration existingConfig : existingConfigs) {
			if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
				return existingConfig;
			}
		}
		return null;
	}

	private WifiConfiguration createWifiInfo(String SSID, String Password,
			WifiCipherType Type) {
		WifiConfiguration config = new WifiConfiguration();
		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();
		config.SSID = "\"" + SSID + "\"";
		if (Type == WifiCipherType.WIFICIPHER_NOPASS) {
			config.wepKeys[0] = "";
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (Type == WifiCipherType.WIFICIPHER_WEP) {
			config.preSharedKey = "\"" + Password + "\"";
			config.hiddenSSID = true;
			config.allowedAuthAlgorithms
					.set(WifiConfiguration.AuthAlgorithm.SHARED);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
			config.allowedGroupCiphers
					.set(WifiConfiguration.GroupCipher.WEP104);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (Type == WifiCipherType.WIFICIPHER_WPA) {

			// config.preSharedKey = "\"" + Password + "\"";
			// config.hiddenSSID = true;
			// config.allowedAuthAlgorithms
			// .set(WifiConfiguration.AuthAlgorithm.OPEN);
			// config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			// config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			// config.allowedPairwiseCiphers
			// .set(WifiConfiguration.PairwiseCipher.TKIP);
			// config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			// config.status = WifiConfiguration.Status.ENABLED;

			// �޸�֮������
			config.preSharedKey = "\"" + Password + "\"";
			config.hiddenSSID = true;
			config.allowedAuthAlgorithms
					.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			config.allowedPairwiseCiphers
					.set(WifiConfiguration.PairwiseCipher.TKIP);
			// config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedPairwiseCiphers
					.set(WifiConfiguration.PairwiseCipher.CCMP);

		} else {
			return null;
		}
		return config;
	}

	/**
	 * Function:�ж�ɨ�����Ƿ�������<br>
	 * 
	 * @author ZYT DateTime 2014-5-14 ����11:31:40<br>
	 * @param result
	 * @return<br>
	 */
	public boolean isConnect(ScanResult result) {
		if (result == null) {
			return false;
		}

		mWifiInfo = mWifiManager.getConnectionInfo();
		String g2 = "\"" + result.SSID + "\"";
		if (mWifiInfo.getSSID() != null && mWifiInfo.getSSID().endsWith(g2)) {
			return true;
		}
		return false;
	}

	/**
	 * Function: ��int���͵�IPת�����ַ�����ʽ��IP<br>
	 * 
	 * @author ZYT DateTime 2014-5-14 ����12:28:16<br>
	 * @param ip
	 * @return<br>
	 */
	public String ipIntToString(int ip) {
		try {
			byte[] bytes = new byte[4];
			bytes[0] = (byte) (0xff & ip);
			bytes[1] = (byte) ((0xff00 & ip) >> 8);
			bytes[2] = (byte) ((0xff0000 & ip) >> 16);
			bytes[3] = (byte) ((0xff000000 & ip) >> 24);
			return Inet4Address.getByAddress(bytes).getHostAddress();
		} catch (Exception e) {
			return "";
		}
	}

	public int getConnNetId() {
		// result.SSID;
		mWifiInfo = mWifiManager.getConnectionInfo();
		return mWifiInfo.getNetworkId();
	}

	/**
	 * Function:�ź�ǿ��ת��Ϊ�ַ���<br>
	 * 
	 * @author ZYT DateTime 2014-5-14 ����2:14:42<br>
	 * @param level
	 * <br>
	 */
	public static String singlLevToStr(int level) {

		String resuString = "���ź�";

		if (Math.abs(level) > 100) {
		} else if (Math.abs(level) > 80) {
			resuString = "��";
		} else if (Math.abs(level) > 70) {
			resuString = "ǿ";
		} else if (Math.abs(level) > 60) {
			resuString = "ǿ";
		} else if (Math.abs(level) > 50) {
			resuString = "��ǿ";
		} else {
			resuString = "��ǿ";
		}
		return resuString;
	}

	/**
	 * ��ӵ�����
	 * @param wcg
	 */
	public boolean addNetwork(WifiConfiguration wcg) {
		if (wcg == null) {
			return false;
		}
		//receiverDhcp = new ReceiverDhcp(ctx, mWifiManager, this, wlanHandler);
		//ctx.registerReceiver(receiverDhcp, new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION));
		int wcgID = mWifiManager.addNetwork(wcg);
		boolean b = mWifiManager.enableNetwork(wcgID, true);
		mWifiManager.saveConfiguration();
		System.out.println(b);
		return b;
	}
	
	public boolean connectSpecificAP(ScanResult scan) {
		List<WifiConfiguration> list = mWifiManager.getConfiguredNetworks();
		boolean networkInSupplicant = false;
		boolean connectResult = false;
		// ��������ָ��AP
		mWifiManager.disconnect();
		for (WifiConfiguration w : list) {
			// ��ָ��AP ����ת��
			// String str = convertToQuotedString(info.ssid);
			if (w.BSSID != null && w.BSSID.equals(scan.BSSID)) {
				connectResult = mWifiManager.enableNetwork(w.networkId, true);
				// mWifiManager.saveConfiguration();
				networkInSupplicant = true;
				break;
			}
		}
		if (!networkInSupplicant) {
			WifiConfiguration config = CreateWifiInfo(scan, "");
			connectResult = addNetwork(config);
		}

		return connectResult;
	}

	// Ȼ����һ��ʵ��Ӧ�÷�����ֻ��֤��û������������
	public WifiConfiguration CreateWifiInfo(ScanResult scan, String Password) {
		// Password="ultrapower2013";
		// deleteExsits(info.ssid);
		WifiConfiguration config = new WifiConfiguration();
		config.hiddenSSID = false;
		config.status = WifiConfiguration.Status.ENABLED;

		if (scan.capabilities.contains("WEP")) {
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.allowedAuthAlgorithms
					.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedGroupCiphers
					.set(WifiConfiguration.GroupCipher.WEP104);

			config.SSID = "\"" + scan.SSID + "\"";

			config.wepTxKeyIndex = 0;
			config.wepKeys[0] = Password;
			// config.preSharedKey = "\"" + SHARED_KEY + "\"";
		} else if (scan.capabilities.contains("PSK")) {
			//
			config.SSID = "\"" + scan.SSID + "\"";
			config.preSharedKey = "\"" + Password + "\"";
		} else if (scan.capabilities.contains("EAP")) {
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);
			config.allowedAuthAlgorithms
					.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedPairwiseCiphers
					.set(WifiConfiguration.PairwiseCipher.TKIP);
			config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			config.SSID = "\"" + scan.SSID + "\"";
			config.preSharedKey = "\"" + Password + "\"";
		} else {
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

			config.SSID = "\"" + scan.SSID + "\"";
			// config.BSSID = info.mac;
			config.preSharedKey = null;
			//
		}

		return config;
	}

}
