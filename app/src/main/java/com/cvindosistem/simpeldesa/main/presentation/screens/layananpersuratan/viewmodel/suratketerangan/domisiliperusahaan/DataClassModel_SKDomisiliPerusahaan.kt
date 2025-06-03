package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisiliperusahaan

data class PersonalInfo(
    val nik: String = "",
    val nama: String = "",
    val tempatLahir: String = "",
    val tanggalLahir: String = "",
    val jenisKelamin: String = "",
    val agama: String = "",
    val alamat: String = "",
    val pekerjaan: String = ""
)

data class CompanyInfo(
    val namaPerusahaan: String = "",
    val jenisUsaha: String = "",
    val bidangUsaha: String = "",
    val nomorAkta: String = "",
    val statusKepemilikanBangunan: String = "",
    val jumlahKaryawan: String = "",
    val alamatPerusahaan: String = "",
    // Warga Desa specific
    val nib: String = "",
    // Pendatang specific
    val peruntukanBangunan: String = "",
    val luasTanah: Int = 0,
    val luasBangunan: Int = 0,
    val npwp: String = ""
)

data class AdditionalInfo(
    val keperluan: String = ""
)

data class FormData(
    val personalInfo: PersonalInfo = PersonalInfo(),
    val companyInfo: CompanyInfo = CompanyInfo(),
    val additionalInfo: AdditionalInfo = AdditionalInfo()
)

data class FormState(
    val currentTab: Int = 0,
    val currentStepWargaDesa: Int = 1,
    val currentStepPendatang: Int = 1,
    val useMyDataChecked: Boolean = false,
    val wargaData: FormData = FormData(),
    val pendatangData: FormData = FormData()
)